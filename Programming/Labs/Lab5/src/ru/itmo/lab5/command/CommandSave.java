package ru.itmo.lab5.command;

public class CommandSave extends AbstractCommand
{
	public void execute(String... args) 
	{
		commandHandler.getCollectionHandler().saveCollection();
	}

	public String getInfo() 
	{
		return "saves collection to file";
	}

	public int argsCount() 
	{
		return 0;
	}	
}
