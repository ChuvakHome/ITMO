package ru.itmo.lab6.command;

import java.util.stream.Stream;

import ru.itmo.lab6.collection.ProductHandler;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;

public class CommandInfo extends AbstractCollectionCommand<Command.Args>
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;

	public void execute(Args args) 
	{
		Printer.printfln(Printer.OUT, "TOTAL SIZE: %d", collection.size());
		
		if (collection.isEmpty())
			return;
			
		ProductHandler.STANDARD_FIELDS.forEach(field -> Printer.OUT.printf("/ %s.%s ", field.getDeclaringClass().getSimpleName(), field.getName()));
		
		Printer.OUT.println("/");
		
		collection.stream().forEach(product ->
		{
			Stream.of(ProductHandler.readFieldsFromProduct(product)).forEach(value -> Printer.OUT.printf("/ %s ", value));
			Printer.OUT.println("/");
		});
	}
	
	public String getInfo() 
	{
		return "prints in standard out info about collection (type, creation date, size etc)";
	}
}
