package ru.itmo.lab6.command;

import java.util.Iterator;

import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;

public class CommandHelp extends AbstractClientCommand<Command.Args>
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public void execute(Args args) 
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
}
