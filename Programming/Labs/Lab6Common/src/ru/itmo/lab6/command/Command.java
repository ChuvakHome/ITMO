package ru.itmo.lab6.command;

import java.io.Serializable;

import ru.itmo.lab6.util.Constants;

public interface Command extends Serializable
{
	Class<? extends Args> getArgsList();
	
	void execute(Object args);
	
	String getName();
	
	String getInfo();
	
	int standardArgsCount();
	
	void setCommandHandler(CommandHandler commandHandler);
	
	CommandType getCommandType();
	
	public static class Args implements Serializable
	{
		private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND_ARGS;
	}
	
	public static enum CommandType
	{
		SERVER_COMMAND,
		COLLECTION_COMMAND,
		CLIENT_COMMAND
	}
}
