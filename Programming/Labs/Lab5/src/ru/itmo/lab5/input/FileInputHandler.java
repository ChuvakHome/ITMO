package ru.itmo.lab5.input;

import ru.itmo.lab5.command.CommandHandler;

public class FileInputHandler extends InputHandler
{
	public FileInputHandler(CommandHandler commandHandler) 
	{
		super(commandHandler);
	}
	
	public boolean needInvition()
	{
		return false;
	}
}
