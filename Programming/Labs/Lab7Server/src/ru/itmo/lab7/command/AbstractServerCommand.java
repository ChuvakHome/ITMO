package ru.itmo.lab7.command;

import java.nio.channels.SocketChannel;

import ru.itmo.lab6.command.AbstractCommand;
import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.command.CommandHandler;

public abstract class AbstractServerCommand<E extends Command.Args> extends AbstractCommand<E>
{
	private static final long serialVersionUID = 1L;

	protected transient SocketChannel socketChannel;
	protected transient ServerCommandHandler serverCommandHandler;
	
	public AbstractServerCommand()
	{
		super();
	}
	
	public AbstractServerCommand(String name)
	{
		super(name);
	}
	
	public void setSocketChannel(SocketChannel socketChannel)
	{
		this.socketChannel = socketChannel;
	}
	
	public void setCommandHandler(CommandHandler commandHandler)
	{
		super.setCommandHandler(commandHandler);
		
		if (commandHandler instanceof ServerCommandHandler)
			serverCommandHandler = (ServerCommandHandler) commandHandler;
	}
}
