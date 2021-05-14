package ru.itmo.lab5.command;

import java.util.stream.Stream;

import ru.itmo.lab5.util.Printer;

public class CommandHistory extends AbstractCommand 
{
	public void execute(String... args) 
	{
		String[] history = commandHandler.getHistory();
		
		Stream.of(history).filter(command -> command != null).forEachOrdered(Printer.OUT::println);
	}

	public String getInfo() 
	{
		return "prints last 10 executed commands";
	}

	public int argsCount() 
	{
		return 0;
	}

}
