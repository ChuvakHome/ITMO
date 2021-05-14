package ru.itmo.lab5.command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.itmo.lab5.collection.Product;
import ru.itmo.lab5.util.Printer;

public class CommandGroupCountingByCreationDate extends AbstractCommand 
{
	public void execute(String... args) 
	{
		Collection<Product> collection = commandHandler.getCollectionHandler().getCollection();
		
		Map<LocalDateTime, List<Product>> temp = new HashMap<>();
		
		collection.forEach(e ->
		{
			LocalDateTime localDateTime = e.getCreationDate();
			
			if (temp.containsKey(localDateTime))
				temp.get(localDateTime).add(e);
			else
			{
				temp.put(localDateTime, new ArrayList<>());
				temp.get(localDateTime).add(e);
			}
		});
		
		collection.clear();
		
		temp.entrySet().forEach(entry -> 
		{
			collection.addAll(entry.getValue());
			Printer.OUT.println(entry.getKey() + ": " + entry.getValue().size());
		});
	}

	public String getInfo() 
	{
		return "groups all elements by \'creationDate\' field and prints how many elements in each groups";
	}

	public int argsCount() 
	{
		return 0;
	}
}
