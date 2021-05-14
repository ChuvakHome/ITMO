package ru.itmo.lab5.command;

import ru.itmo.lab5.collection.Product;
import ru.itmo.lab5.util.Printer;

public class CommandRemoveHead extends AbstractCommand 
{
	public void execute(String... args) 
	{		
		if (!commandHandler.getCollectionHandler().getCollection().isEmpty())
		{
			Product head = commandHandler.getCollectionHandler().getCollection().peek();
			Printer.OUT.println(head);
			commandHandler.getCollectionHandler().removeIf(product -> product.getId().equals(head.getId()));
		}
	}

	public String getInfo() 
	{
		return "prints the first element of collection and removes the element";
	}

	public int argsCount() 
	{
		return 0;
	}
}
