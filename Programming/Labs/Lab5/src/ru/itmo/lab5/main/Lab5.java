package ru.itmo.lab5.main;

import ru.itmo.lab5.collection.CollectionHandler;
import ru.itmo.lab5.command.CommandHandler;
import ru.itmo.lab5.input.ConsoleInputHandler;
import ru.itmo.lab5.input.InputHandler;
import ru.itmo.lab5.logging.LogHandler;
import ru.itmo.lab5.output.OutputHandler;

public class Lab5 
{	
	public static void main(String... args)
	{		
		LogHandler.init("logger.config");
		
		OutputHandler outputHandler = new OutputHandler();
		CollectionHandler collectionHandler = new CollectionHandler(outputHandler);
	
		CommandHandler commandHandler = new CommandHandler(collectionHandler);
		InputHandler inputHandler = new ConsoleInputHandler(commandHandler);
		
		collectionHandler.fillCollection();
		
		inputHandler.input(System.in);
	}
}
