package ru.itmo.lab7.command;

import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.util.Constants;

public class CommandSave extends AbstractServerCommand<Command.Args>
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public void execute(Args args) 
	{
		serverCommandHandler.getCollectionHandler().saveCollection();
	}

	public String getInfo() 
	{
		return "saves collection to file";
	}
}
