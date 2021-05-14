package ru.itmo.lab6.command;

public abstract class AbstractServerCommand<E extends Command.Args> extends AbstractCommand<E>
{
	private static final long serialVersionUID = 1L;

	protected transient ServerCommandHandler serverCommandHandler;
	
	public AbstractServerCommand()
	{
		super();
	}
	
	public AbstractServerCommand(String name)
	{
		super(name);
	}
	
	public void setCommandHandler(CommandHandler commandHandler)
	{
		super.setCommandHandler(commandHandler);
		
		if (commandHandler instanceof ServerCommandHandler)
			serverCommandHandler = (ServerCommandHandler) commandHandler;
	}
}
