package ru.itmo.lab6.command;

import ru.itmo.lab6.util.Constants;

public class CommandClear extends AbstractCommand<Command.Args> 
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public String getInfo() 
	{
		return "clears collection";
	}
}
