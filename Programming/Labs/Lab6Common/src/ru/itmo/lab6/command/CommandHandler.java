package ru.itmo.lab6.command;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public interface CommandHandler 
{
	static final String EXIT_COMMAND_NAME = "exit";
	
	String[] getHistory();
	
	List<String> getList();
	
	boolean isScriptExecuting();
	
	String getCurrentScript();
	
	void executeScript(File scriptFile);
	
	void executeCommandByName(String command, Command.Args args);
	
	void executeCommand(Command command, Command.Args args);
	
	Command getCommandByName(String command);
	
	void addCommands(Command... commands);
	
	Iterator<Command> getCommandsIterator();
}
