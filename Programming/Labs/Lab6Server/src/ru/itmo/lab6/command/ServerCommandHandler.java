package ru.itmo.lab6.command;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ru.chuvak.classfinder.ClassFinder;
import ru.itmo.lab6.collection.CollectionHandler;
import ru.itmo.lab6.input.FileInputHandler;
import ru.itmo.lab6.server.Server;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;

public class ServerCommandHandler implements CommandHandler
{
	private int key = 0;
	private String[] history = new String[10];
	private Map<String, Command> commandDict = new HashMap<>();
	
	private CollectionHandler collectionHandler;
	private FileInputHandler inputHandler; 
	private Server server;

	private boolean executingScript = false;
	private String currentScript;
	
	private List<String> scriptList;
	
	public ServerCommandHandler(CollectionHandler collectionHandler)
	{
		this.inputHandler = new FileInputHandler(this);
		this.collectionHandler = collectionHandler;
		
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
		ClassFinder.ALL_CLASSES.stream().filter(tinyClass -> tinyClass.getSimpleName().startsWith("Command")).forEach(tinyClass ->
		{
			Class<?> clazz = tinyClass.toClass();
			int mod = clazz.getModifiers();
			
			if (!Modifier.isAbstract(mod) && !Modifier.isInterface(mod) && Command.class.isAssignableFrom(clazz))
			{
				try 
				{
					addCommands((Command) clazz.newInstance());
				} catch (Exception e) {}
			}
		});
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
	
	public void executeCommandByName(String command, Command.Args args)
	{
		if (command != null)
		{
			if (commandDict.containsKey(command))
				executeCommand(getCommandByName(command), args);
		}	
	}
	
	public void executeCommand(Command command, Command.Args args)
	{
		if (command != null)
		{
			addToHistory(command.getName());
			
			if (command instanceof CollectionCommand)
			{
				CollectionCommand collectionCommand = (CollectionCommand) command;
				collectionCommand.setCollection(collectionHandler.getCollection());
			}

			if (command instanceof CommandExit)
				collectionHandler.saveCollection();
				
			command.execute(args);
		}
	}
	
	public void executeClientCommandByName(String command, Command.Args args)
	{
		if (command != null)
		{
			if (commandDict.containsKey(command))
				executeClientCommand(getCommandByName(command), args);
		}	
	}
	
	public void executeClientCommand(Command command, Command.Args args)
	{
		if (command != null)
			command.execute(args);
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
			command.setCommandHandler(this);
		});
	}
	
	public Iterator<Command> getCommandsIterator()
	{
		return commandDict.values().iterator();
	}
	
	public CollectionHandler getCollectionHandler()
	{
		return collectionHandler;
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
	
	public void setServer(Server server)
	{
		this.server = server;
	}
	
	public Server getServer()
	{
		return server;
	}
}
