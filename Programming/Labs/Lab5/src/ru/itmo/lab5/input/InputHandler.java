package ru.itmo.lab5.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import ru.itmo.lab5.command.Command;
import ru.itmo.lab5.command.CommandHandler;
import ru.itmo.lab5.command.ElementCommand;
import ru.itmo.lab5.util.Constants;
import ru.itmo.lab5.util.Printer;
import ru.itmo.lab5.util.Utils;

public abstract class InputHandler
{
	protected CommandHandler commandHandler;
	
	public InputHandler(CommandHandler commandHandler)
	{
		this.commandHandler = commandHandler;
	}
	
	public abstract boolean needInvition();
	
	public void beforeInput() {}
	
	public boolean ready(InputStream inputStream, BufferedReader bufferedReader)
	{
		try 
		{
			if (inputStream == null)
				return false;
			else if (bufferedReader == null)
				return false;
			
			return inputStream.available() >= 0 && bufferedReader.ready();
		} 
		catch (IOException e) 
		{
			return false;
		}
	}
	
	public void input(InputStream inputStream)
	{
		String command = "";
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		while (ready(inputStream, bufferedReader) && !command.equals(CommandHandler.EXIT_COMMAND_NAME))
		{
			beforeInput();
			
			String line = Utils.readLine(bufferedReader);
			
			if (line == null)
				break;
			else if (line.isEmpty())
				continue;
			
			line = line.trim();
			
			String[] parts = line.split("\\s");
			
			command = parts[0];
			
			Command c = commandHandler.getCommandByName(command);
			
			parts = Arrays.copyOfRange(parts, 1, parts.length);
			
			String s = " in script \'%s\'";
			
			s = commandHandler.isScriptExecuting() ? String.format(s, commandHandler.getCurrentScript()) : "";
			
			if (c == null)
			{	
				Printer.printfln(Printer.ERR, Constants.UNKNOWN_COMMAND_MSG, command, s);
				continue;
			}
			else if (parts.length != c.argsCount() || !c.check(parts))
			{
				Printer.printfln(Printer.ERR, Constants.WRONG_USAGE_MSG, c.getName(), s);
				continue;
			}
			
			if (c instanceof ElementCommand)
			{
				if (!((ElementCommand) c).readElement(bufferedReader, needInvition()))
				{
					Printer.printfln(Printer.ERR, Constants.WRONG_USAGE_MSG, c.getName(), s);
					continue;
				}
			}
			
			commandHandler.executeCommandByName(command, parts);
		}
		
		Utils.close(bufferedReader);
	}
}