package ru.itmo.lab5.command;

import ru.itmo.lab5.util.Printer;

public class CommandPrintAscending extends AbstractCommand 
{
	public void execute(String... args) 
	{
		commandHandler.getCollectionHandler().getCollection().stream().sorted().forEachOrdered(Printer.OUT::println);
	}

	public String getInfo() 
	{
		return "prints all elements in natural order";
	}

	public int argsCount() 
	{
		return 0;
	}
}
