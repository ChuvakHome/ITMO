package ru.itmo.lab6.command;

public class CommandExit extends AbstractClientCommand<Command.Args>
{
	private static final long serialVersionUID = -6522084499730446986L;
	
	public void execute(Args args) 
	{
		System.exit(0);
	}

	public String getName() 
	{
		return CommandHandler.EXIT_COMMAND_NAME;
	}

	public String getInfo() 
	{
		return "exits without saving to file";
	}
}
