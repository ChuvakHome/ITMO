package ru.itmo.lab7.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.packet.client.ClientPacket;
import ru.itmo.lab6.packet.client.PacketExecuteCommand;
import ru.itmo.lab6.packet.client.PacketSendCollection;
import ru.itmo.lab6.packet.client.PacketStartSession;
import ru.itmo.lab6.packet.client.PacketStopSession;
import ru.itmo.lab6.packet.server.PacketConnectionEstablished;
import ru.itmo.lab6.packet.server.PacketReceiveCollection;
import ru.itmo.lab6.packet.server.ServerPacket;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;
import ru.itmo.lab7.command.AbstractServerCommand;
import ru.itmo.lab7.command.ServerCommandHandler;

public class Server 
{
	private int port;
	
	private ServerCommandHandler commandHandler;
	
	public Server(ServerCommandHandler commandHandler)
	{
		this(Constants.STANDARD_PORT, commandHandler);
	}
	
	public Server(int port, ServerCommandHandler commandHandler)
	{
		this.port = port;
		this.commandHandler = commandHandler;
		this.commandHandler.setServer(this);
	}
	
	public void startServer()
	{
		InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
		
		try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open())
		{
			Selector selector = Selector.open();
			
			serverSocketChannel.bind(inetSocketAddress);
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			Runtime.getRuntime().addShutdownHook(new Thread(() -> 
			{
				try 
				{
					serverSocketChannel.close();
				} 
				catch (Exception e) {}
			}));
			
			while (true)
			{
				selector.select();
				
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				
				while (iterator.hasNext())
				{
					SelectionKey selectionKey = iterator.next();
					iterator.remove();
					
					if (selectionKey.isValid())
					{
						if (selectionKey.isAcceptable())
						{
							SocketChannel socketChannel = serverSocketChannel.accept();
							socketChannel.configureBlocking(false);
							socketChannel.register(selector, SelectionKey.OP_READ);
						}
						else if (selectionKey.isReadable() && selectionKey.channel() instanceof SocketChannel)
							processRead((SocketChannel) selectionKey.channel());
					}
				}
			}
		}
//		catch (EOFException e) {}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		startServer();
	}
	
	private void processRead(SocketChannel socketChannel)
	{		
		try
		{
			ByteBuffer buffer = ByteBuffer.allocate(socketChannel.getOption(StandardSocketOptions.SO_RCVBUF).intValue());
			socketChannel.read(buffer);
			
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			
			Object obj = objectInputStream.readObject();
			
			if (obj instanceof ClientPacket)
				processPacket(socketChannel, (ClientPacket) obj);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void processPacket(SocketChannel socketChannel, ClientPacket packet)
	{
		try
		{
			if (packet instanceof PacketStartSession)
			{
				Printer.OUT.println("Connection established");
				sendPacket(socketChannel, new PacketConnectionEstablished());
			}
			else if (packet instanceof PacketStopSession)
			{
				Printer.OUT.println("Connection lost");
				socketChannel.close();
			}
			else if (packet instanceof PacketExecuteCommand<?>)
			{
				PacketExecuteCommand<?> packetExecuteCommand = (PacketExecuteCommand<?>) packet;
				
				Command command = packetExecuteCommand.getCommand();
				command.setCommandHandler(commandHandler);
				
				if (command instanceof AbstractServerCommand<?>)
					((AbstractServerCommand<?>) command).setSocketChannel(socketChannel);
				
				commandHandler.executeClientCommand(packetExecuteCommand.getCommand(), packetExecuteCommand.getArgs());
			}
			else if (packet instanceof PacketSendCollection)
				sendCollection(socketChannel, (PacketSendCollection) packet);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendCollection(SocketChannel socketChannel, PacketSendCollection packet)
	{
		sendPacket(socketChannel, new PacketReceiveCollection(packet.getCollectionCommand(), packet.getArgs(), commandHandler.getCollectionHandler().getCollection().stream().sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName())).toArray(Product[]::new)));
	}
	
	public void sendPacket(SocketChannel socketChannel, ServerPacket packet)
	{
		writeObject(socketChannel, packet);
	}
	
	private void writeObject(SocketChannel socketChannel, Object obj)
	{
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);)
		{
			objectOutputStream.writeObject(obj);
			
			socketChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
