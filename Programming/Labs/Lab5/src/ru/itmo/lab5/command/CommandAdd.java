package ru.itmo.lab5.command;

public class CommandAdd extends ElementCommand
{
	public void execute(String... args) 
	{
		commandHandler.getCollectionHandler().addProduct(product);
	}

	public String getInfo() 
	{
		return "adds new {element} to collection";
	}

	public int argsCount() 
	{
		return 0;
	}
}
