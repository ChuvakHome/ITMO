package ru.itmo.lab5.command;

import java.io.File;

public class CommandExecuteScript extends AbstractCommand
{
	public void execute(String... args) 
	{
		File f = new File(args[0]);
		
		commandHandler.executeScript(f);
	}

	public String getInfo() 
	{
		return "executes a scipt file";
	}

	public int argsCount() 
	{
		return 1;
	}
}
