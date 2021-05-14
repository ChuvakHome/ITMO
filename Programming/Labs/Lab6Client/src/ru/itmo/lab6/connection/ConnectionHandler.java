package ru.itmo.lab6.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.command.ClientCommandHandler;
import ru.itmo.lab6.command.CollectionCommand;
import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.command.CommandRemoveHead;
import ru.itmo.lab6.logging.LogHandler;
import ru.itmo.lab6.packet.client.ClientPacket;
import ru.itmo.lab6.packet.client.PacketExecuteCommand;
import ru.itmo.lab6.packet.client.PacketSendCollection;
import ru.itmo.lab6.packet.client.PacketStartSession;
import ru.itmo.lab6.packet.client.PacketStopSession;
import ru.itmo.lab6.packet.server.PacketCommandResult;
import ru.itmo.lab6.packet.server.PacketConnectionEstablished;
import ru.itmo.lab6.packet.server.PacketReceiveCollection;
import ru.itmo.lab6.packet.server.ServerPacket;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;

public class ConnectionHandler 
{
	private String address;
	private int port;
	
	private volatile boolean active;
	
	private SocketChannel socketChannel;
	private ClientCommandHandler commandHandler;
	
	public ConnectionHandler(String address, int port, ClientCommandHandler commandHandler)
	{
		this.address = address;
		this.port = port;
		
		this.commandHandler = commandHandler;
	}
	
	public void connectToServer()
	{
		InetSocketAddress inetAddress = new InetSocketAddress(address, port);
		
		try
		{
			Selector selector = Selector.open();
			socketChannel = SocketChannel.open(inetAddress);
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			
			Runtime.getRuntime().addShutdownHook(new Thread(() -> 
			{
				try 
				{	
					if (socketChannel != null)
					{
						sendPacket(new PacketStopSession());
						socketChannel.close();
					}
				} 
				catch (Exception e) {}
			}));
			
			new Thread(() -> 
			{
				active = true;
				
				sendPacket(new PacketStartSession());
				
				try
				{					
					while (active)
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
								if (selectionKey.isReadable())
									processRead();
							}
						}
					}
					
					selector.close();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}).start();
		}
		catch (ConnectException e) 
		{
			printServerUnavaliableMsg();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void processRead()
	{	
		Object obj = readObject();
		
		if (obj instanceof ServerPacket)
			processServerPacket((ServerPacket) obj);
	}
	
	private void processServerPacket(ServerPacket packet)
	{
		if (packet instanceof PacketConnectionEstablished)
			Printer.OUT.println("Connection with server established");
		else if (packet instanceof PacketReceiveCollection)
		{
			PacketReceiveCollection packetReceiveCollection = (PacketReceiveCollection) packet;
			
			CollectionCommand collectionCommand = packetReceiveCollection.getCollectionCommand();
			collectionCommand.setCollection(new ArrayDeque<Product>(Arrays.asList(packetReceiveCollection.getProducts())));
			collectionCommand.setCommandHandler(commandHandler);
			collectionCommand.execute(packetReceiveCollection.getArgs());
		}
		else if (packet instanceof PacketCommandResult<?>)
		{
			PacketCommandResult<?> packetCommandResult = (PacketCommandResult<?>) packet;
			
			if (packetCommandResult.getCommand() instanceof CommandRemoveHead)
				Printer.OUT.println(packetCommandResult.getResult());
		}
	}
	
	//Fix
	public boolean serverAvaliable()
	{
		if (socketChannel == null)
			return false;
		
		try
		{
			socketChannel.write(ByteBuffer.allocate(0));
			
			return true;
		}
		catch (Exception e) 
		{
			return false;
		}
	}
	
	public String getServerAddress()
	{
		return address;
	}
	
	public int getServerPort()
	{
		return port;
	}
	
	public void closeConnection()
	{
		if (serverAvaliable())
			sendPacket(new PacketStopSession());
		
		active = false;
	}
	
	public void sendCollectionRequest(CollectionCommand command, Command.Args args)
	{
		sendPacket(new PacketSendCollection(command, args));
	}
	
	public void sendCommand(Command command, Command.Args args)
	{
		sendPacket(new PacketExecuteCommand<Command.Args>(command, args));
	}
	
	private Object readObject()
	{	
		if (serverAvaliable())
		{
			try
			{
				ByteBuffer buffer = ByteBuffer.allocate(socketChannel.getOption(StandardSocketOptions.SO_RCVBUF).intValue());
				
				socketChannel.read(buffer);
				
				byte[] b = buffer.array().clone();
				
				ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(b));
				Object obj = objectInputStream.readObject();
				
				objectInputStream.close();
				
				return obj;
			}
			catch (StreamCorruptedException e) {e.printStackTrace();}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public void sendPacket(ClientPacket packet)
	{
		if (packet != null)
		{
			if (serverAvaliable())
				writeObject(packet);
			else
			{
				connectToServer();
					
				if (serverAvaliable())
					writeObject(packet);
				else
					printServerUnavaliableMsg("Client cannot send packet (" + packet.getClass().getSimpleName() + ")");
			}
		}
	}
	
	private void writeObject(Object obj)
	{
		if (!serverAvaliable())
		{			
			connectToServer();
			
			if (!serverAvaliable())
			{
				printServerUnavaliableMsg("Client cannot write object");
				return;
			}
		}
		
		try
		{
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			objectOutputStream.close();
			
			byte[] b = byteArrayOutputStream.toByteArray();
			
			ByteBuffer byteBuffer = ByteBuffer.wrap(b);
			
			if (socketChannel != null && socketChannel.isOpen())
				socketChannel.write(byteBuffer);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void printServerUnavaliableMsg()
	{
		printServerUnavaliableMsg("");
	}
	
	private void printServerUnavaliableMsg(String msg)
	{
		if (msg == null || msg.isEmpty())
			msg = "";
		else
			msg += ": ";
		
		LogHandler.LOGGER.log(Level.SEVERE, String.format(msg + Constants.SERVER_UNAVALIABLE, getServerAddress(), getServerPort()));
	}
}
