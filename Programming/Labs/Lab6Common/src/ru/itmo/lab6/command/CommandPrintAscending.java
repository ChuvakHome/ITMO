package ru.itmo.lab6.command;

import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;

public class CommandPrintAscending extends AbstractCollectionCommand<Command.Args>
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;

	public void execute(Args args) 
	{
		collection.stream().sorted().forEachOrdered(Printer.OUT::println);
	}
	
	public String getInfo() 
	{
		return "prints all elements in natural order";
	}
}
