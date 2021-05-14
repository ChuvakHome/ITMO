package ru.itmo.lab5.command;

public class CommandRemoveLower extends ElementCommand 
{
	public void execute(String... args) 
	{
		commandHandler.getCollectionHandler().removeIf(e -> product.compareTo(e) > 0);
	}

	public String getInfo() 
	{
		return "removes all elements less than {element}";
	}

	public int argsCount() 
	{
		return 0;
	}
}
