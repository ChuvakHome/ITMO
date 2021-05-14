package ru.itmo.lab6.main;

import ru.itmo.lab6.collection.CollectionHandler;
import ru.itmo.lab6.command.ServerCommandHandler;
import ru.itmo.lab6.input.ConsoleInputHandler;
import ru.itmo.lab6.input.InputHandler;
import ru.itmo.lab6.logging.LogHandler;
import ru.itmo.lab6.output.OutputHandler;
import ru.itmo.lab6.server.Server;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Converter;

public class Lab6Server 
{	
	public static void main(String... args)
	{		
		int port = Constants.STANDARD_PORT;
		
		if (args.length > 0 && Converter.isParsable(Integer.class, args[0]))
			port = Integer.parseInt(args[0]);
		
		LogHandler.init("logger.config");
	
		OutputHandler outputHandler = new OutputHandler();
		CollectionHandler collectionHandler = new CollectionHandler(outputHandler);
		
		ServerCommandHandler commandHandler = new ServerCommandHandler(collectionHandler);
		InputHandler inputHandler = new ConsoleInputHandler(commandHandler);
		
		collectionHandler.fillCollection();
		
		Server server = new Server(port, commandHandler);
		
		new Thread(() -> inputHandler.input(System.in)).start();
		
		server.startServer();
	}
}
