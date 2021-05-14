package ru.itmo.lab5.command;

public class CommandInfo extends AbstractCommand
{
	public void execute(String... args) 
	{
		commandHandler.getCollectionHandler().printCollection();
	}

	public String getInfo() 
	{
		return "prints in standard out info about collection (type, creation date, size etc)";
	}
	
	public int argsCount()
	{
		return 0;
	}
}
