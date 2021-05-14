package ru.itmo.lab5.command;

import java.util.Iterator;

import ru.itmo.lab5.util.Printer;

public class CommandHelp extends AbstractCommand
{
	public void execute(String... args) 
	{
		Iterator<Command> iter = commandHandler.getCommandsIterator();
		
		Command c;
		
		while (iter.hasNext())
		{
			c = iter.next();
			
			Printer.OUT.printf("%s: %s\n", c.getName(), c.getInfo());
		}
	}

	public String getInfo() 
	{
		return "prints manual for commands";
	}
	
	public int argsCount()
	{
		return 0;
	}
}
