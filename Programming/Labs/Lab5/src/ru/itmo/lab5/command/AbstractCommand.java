package ru.itmo.lab5.command;

public abstract class AbstractCommand implements Command 
{
	protected CommandHandler commandHandler;
	
	public void setCommandHandler(CommandHandler commandHandler)
	{
		this.commandHandler = commandHandler;
	}
	
	public CommandHandler getCommandHandler()
	{
		return commandHandler;
	}
	
	public boolean check(String... args)
	{
		return true;
	}
	
	public String getName()
	{
		String className = getClass().getSimpleName().replace("Command", "");
		
		className = Character.toLowerCase(className.charAt(0)) + className.substring(1);
		
		for (char c = 'A'; c <= 'Z'; ++c)
			className = className.replace("" + c, "_" + Character.toLowerCase(c));
		
		return className;
	}
}
