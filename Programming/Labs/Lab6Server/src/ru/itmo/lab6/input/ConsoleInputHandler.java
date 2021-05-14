package ru.itmo.lab6.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import ru.itmo.lab6.command.CommandHandler;

public class ConsoleInputHandler extends InputHandler 
{
	public ConsoleInputHandler(CommandHandler commandHandler)
	{
		super(commandHandler);
	}
	
	public boolean ready(InputStream inputStream, BufferedReader bufferedReader)
	{
		try
		{
			return inputStream.available() >= 0;
		} catch (IOException e) {return false;}
	}
	
	public boolean needInvitation()
	{
		return true;
	}
}
