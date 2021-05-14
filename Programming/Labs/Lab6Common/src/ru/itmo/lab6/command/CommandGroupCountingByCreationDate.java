package ru.itmo.lab6.command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;

public class CommandGroupCountingByCreationDate extends AbstractCollectionCommand<Command.Args> 
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;

	public void execute(Args args) 
	{
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
}
