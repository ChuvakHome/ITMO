package ru.itmo.lab5.command;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ru.itmo.lab5.collection.CollectionHandler;
import ru.itmo.lab5.input.FileInputHandler;
import ru.itmo.lab5.util.Constants;
import ru.itmo.lab5.util.Printer;

public class CommandHandler 
{
	private int key = 0;
	private String[] history = new String[10];
	private Map<String, Command> commandDict = new HashMap<>();

	private CollectionHandler collectionHandler;
	private FileInputHandler inputHandler;
	
	public static final String EXIT_COMMAND_NAME = "exit"; 

	private boolean executingScript = false;
	private String currentScript;
	
	private List<String> scriptList;
	
	public CommandHandler(CollectionHandler collectionHandler)
	{
		this.collectionHandler = collectionHandler;
		this.inputHandler = new FileInputHandler(this);
		this.scriptList = new ArrayList<String>();
		
		loadCommands();
	}
	
	public String[] getHistory()
	{
		return history;
	}
	
	public List<String> getList()
	{
		return scriptList;
	}
	
	public void loadCommands()
	{
		addCommands(new CommandAdd(),
					new CommandClear(),
					new CommandExecuteScript(),
					new CommandExit(),
					new CommandGroupCountingByCreationDate(),
					new CommandHelp(),
					new CommandHistory(),
					new CommandInfo(),
					new CommandPrintAscending(),
					new CommandRemoveAllByPartNumber(),
					new CommandRemoveById(),
					new CommandRemoveHead(),
					new CommandRemoveLower(),
					new CommandSave(),
					new CommandShow(),
					new CommandUpdate());
	}
	
	public boolean isScriptExecuting()
	{
		return executingScript;
	}
	
	public String getCurrentScript()
	{
		return currentScript;
	}
	
	public void executeScript(File scriptFile)
	{		
		if (scriptFile != null && scriptFile.exists())
		{
			if (!scriptFile.canRead())
			{
				Printer.printfln(Printer.ERR, Constants.UNABLE_TO_READ_FILE, scriptFile.getAbsolutePath());
				return;
			}
			
			boolean flag = executingScript;
			String previousScript = currentScript;
			
			try
			{
				executingScript = true;
				
				if (scriptList.contains(scriptFile.getAbsolutePath()))
				{
					Printer.ERR.println("Stack overflow!!!");
					return;
				}
				else
				{
					scriptList.add(scriptFile.getAbsolutePath());
					currentScript = scriptFile.getPath();
					inputHandler.input(new FileInputStream(scriptFile));
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace(Printer.ERR);
			}
			
			if (flag)
				currentScript = previousScript;
			else
			{
				scriptList.clear();
				executingScript = false;
				currentScript = null;
			}
		}
	}
	
	public void executeCommandByName(String command, String... args)
	{
		if (command != null)
		{
			if (commandDict.containsKey(command))
			{
				addToHistory(command);
				commandDict.get(command).execute(args);
			}
		}	
	}
	
	public void executeCommand(Command command, String[] args)
	{
		if (command != null)
		{
			command.execute(args);
			addToHistory(command.getName());
		}
	}
	
	public Command getCommandByName(String command)
	{
		return commandDict.get(command);
	}
	
	public void addCommands(Command... commands)
	{
		Arrays.asList(commands).forEach(command -> 
		{
			commandDict.put(command.getName(), command);
			
			if (command instanceof AbstractCommand)
				((AbstractCommand) command).setCommandHandler(this);
		});
	}
	
	public Iterator<Command> getCommandsIterator()
	{
		return commandDict.values().iterator();
	}
	
	private void addToHistory(String command)
	{
		if (key >= 10)
		{
			System.arraycopy(history, 1, history, 0, 9);
			history[9] = command;
		}
		else
			history[key++] = command;
	}
	
	public CollectionHandler getCollectionHandler()
	{
		return collectionHandler;
	}
}
