package ru.itmo.lab7.command;

import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.util.Constants;

public class CommandClear extends AbstractServerCommand<Command.Args> 
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public void execute(Args args) 
	{
		serverCommandHandler.getCollectionHandler().removeIf(e -> true);
	}
	
	public String getInfo() 
	{
		return "clears collection";
	}
}
