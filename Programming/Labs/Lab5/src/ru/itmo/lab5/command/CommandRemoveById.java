package ru.itmo.lab5.command;

import ru.itmo.lab5.util.Converter;

public class CommandRemoveById extends AbstractCommand 
{
	public boolean check(String... args)
	{
		return Converter.checkFor(args, Integer.class) ? Integer.parseInt(args[0]) > 0 : false;
	}
	
	public void execute(String... args) 
	{	
		commandHandler.getCollectionHandler().removeIf(product -> product.getId().equals(Integer.decode(args[0])));
	}

	public String getInfo() 
	{
		return "removes element with entered id";
	}

	public int argsCount() 
	{
		return 1;
	}
}
