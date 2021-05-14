package ru.itmo.lab6.input;

import ru.itmo.lab6.command.CommandHandler;

public class FileInputHandler extends InputHandler
{
	public FileInputHandler(CommandHandler commandHandler) 
	{
		super(commandHandler);
	}
	
	public boolean needInvitation()
	{
		return false;
	}
}
