package ru.itmo.lab6.command;

import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;

public class CommandShow extends AbstractCollectionCommand<Command.Args> 
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;

	public void execute(Args args) 
	{
		Printer.printfln(Printer.OUT, "TOTAL SIZE: %d", collection.size());
		collection.forEach(Printer.OUT::println);
	}
	
	public String getInfo() 
	{
		return "prints all elements of the collection to standard out";
	}
}
