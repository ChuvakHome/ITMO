package ru.itmo.lab6.command;

import java.util.stream.Stream;

import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;

public class CommandHistory extends AbstractClientCommand<Command.Args>
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public void execute(Args args) 
	{
		String[] history = commandHandler.getHistory();
		
		Stream.of(history).filter(command -> command != null).forEachOrdered(Printer.OUT::println);
	}

	public String getInfo() 
	{
		return "prints last 10 executed commands";
	}
}
