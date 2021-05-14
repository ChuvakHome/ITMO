package ru.itmo.lab5.command;

import java.util.Collection;

import ru.itmo.lab5.collection.Product;
import ru.itmo.lab5.util.Converter;

public class CommandUpdate extends ElementCommand 
{
	public boolean check(String... args)
	{
		return Converter.checkFor(args, Integer.class) ? Integer.parseInt(args[0]) > 0 : false;
	}
	
	public void execute(String... args)
	{
		Integer id = Integer.decode(args[0]);
		Collection<Product> collection = commandHandler.getCollectionHandler().getCollection();

		if (collection.stream().noneMatch(p -> p.getId().equals(id)))
			return;
		
		product.setId(id);
		
		collection.removeIf(e -> e.getId().equals(id));
		collection.add(product);
	}

	public String getInfo() 
	{
		return "updates element of collection with {id}";
	}

	public int argsCount() 
	{
		return 1;
	}
}
