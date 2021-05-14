package ru.itmo.lab6.server;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.command.ServerCommandHandler;
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

public class Server 
{
	private int port;
	
	private Socket socket;
	
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
		try (ServerSocket serverSocket = new ServerSocket(port, 10))
		{
			Runtime.getRuntime().addShutdownHook(new Thread(() -> 
			{
				try 
				{	
					if (socket != null)
					{
						socket.getChannel().close();
						socket.close();
					}
					
					serverSocket.close();
				} 
				catch (Exception e) {}
			}));
			
			socket = serverSocket.accept();
			
			while (true)
			{
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				
				Object obj = objectInputStream.readObject();
				
				if (obj instanceof ClientPacket)
				{
					if (obj instanceof PacketStartSession)
					{
						Printer.OUT.println("Connection established");
						writeObject(new PacketConnectionEstablished());
					}
					else if (obj instanceof PacketStopSession)
					{
						Printer.OUT.println("Connection lost");
						socket.close();
						break;
					}
					else if (obj instanceof PacketExecuteCommand<?>)
					{
						PacketExecuteCommand<?> packet = (PacketExecuteCommand<?>) obj;
						
						Command command = packet.getCommand();
						command.setCommandHandler(commandHandler);
							
						commandHandler.executeClientCommand(packet.getCommand(), packet.getArgs());
					}
					else if (obj instanceof PacketSendCollection)
						sendCollection((PacketSendCollection) obj);
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
	
	public void sendCollection(PacketSendCollection packet)
	{
		sendPacket(new PacketReceiveCollection(packet.getCollectionCommand(), packet.getArgs(), commandHandler.getCollectionHandler().getCollection().stream().sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName())).toArray(Product[]::new)));
	}
	
	public void sendPacket(ServerPacket packet)
	{
		writeObject(packet);
	}
	
	private void writeObject(Object obj)
	{
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);)
		{
			objectOutputStream.writeObject(obj);
			
			byteArrayOutputStream.writeTo(socket.getOutputStream());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
