package ru.itmo.lab5.command;

public class CommandClear extends AbstractCommand 
{
	public void execute(String... args) 
	{
		commandHandler.getCollectionHandler().removeIf(e -> true);
	}
	
	public String getInfo() 
	{
		return "clears collection";
	}

	public int argsCount() 
	{
		return 0;
	}
}
