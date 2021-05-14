package ru.itmo.lab6.main;

import ru.itmo.lab6.command.ClientCommandHandler;
import ru.itmo.lab6.input.ConsoleInputHandler;
import ru.itmo.lab6.input.InputHandler;
import ru.itmo.lab6.logging.LogHandler;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Converter;

public class Lab6Client 
{	
	public static void main(String... args)
	{
		String address = Constants.STANDARD_ADDRESS;
		int port = Constants.STANDARD_PORT;
		
		switch (args.length)
		{
			case 1:
				address = args[0];
				break;
			case 2:
				address = args[1];
				
				if (Converter.isParsable(Integer.class, args[1]))
					port = Integer.parseInt(args[1]);
				break;
		}
		
		LogHandler.init("logger.config");
		
		ClientCommandHandler commandHandler = new ClientCommandHandler(address, port);
		InputHandler inputHandler = new ConsoleInputHandler(commandHandler);
		
		inputHandler.input(System.in);
	}
}
