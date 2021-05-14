package ru.itmo.lab6.command;

public abstract class AbstractClientCommand<E extends Command.Args> extends AbstractCommand<E>
{
	private static final long serialVersionUID = 1L;

	public AbstractClientCommand()
	{
		super();
	}
	
	public AbstractClientCommand(String name)
	{
		super(name);
	}
	
	public CommandType getCommandType()
	{
		return CommandType.CLIENT_COMMAND;
	}
}
