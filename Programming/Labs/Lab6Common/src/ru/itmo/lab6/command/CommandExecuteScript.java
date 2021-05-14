package ru.itmo.lab6.command;

import java.io.File;

import ru.itmo.lab6.util.Constants;

public class CommandExecuteScript extends AbstractClientCommand<CommandExecuteScript.Args>
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public static final class Args extends Command.Args
	{
		private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND_ARGS;
		
		private final String scriptName;
		
		public Args(String scriptName)
		{
			this.scriptName = scriptName;
		}
	}
	
	public void execute(Args args) 
	{
		File f = new File(args.scriptName);
		
		commandHandler.executeScript(f);
	}

	public String getInfo() 
	{
		return "executes a scipt file";
	}

	public int standardArgsCount() 
	{
		return 1;
	}
}
