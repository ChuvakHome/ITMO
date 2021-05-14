package ru.itmo.lab5.command;

public class CommandExit extends AbstractCommand
{
	public void execute(String... args) 
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

	public int argsCount() 
	{
		return 0;
	}

}
