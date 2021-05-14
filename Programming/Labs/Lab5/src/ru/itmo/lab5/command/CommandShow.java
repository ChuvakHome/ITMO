package ru.itmo.lab5.command;

import java.util.Collection;

import ru.itmo.lab5.collection.Product;
import ru.itmo.lab5.util.Printer;

public class CommandShow extends AbstractCommand 
{
	public void execute(String... args) 
	{
		Collection<Product> collection = commandHandler.getCollectionHandler().getCollection();
		
		Printer.printfln(Printer.OUT, "TOTAL SIZE: %d", collection.size());
		collection.forEach(Printer.OUT::println);
	}

	public String getInfo() 
	{
		return "prints all elements of the collection to standard out";
	}

	public int argsCount() 
	{
		return 0;
	}
}
