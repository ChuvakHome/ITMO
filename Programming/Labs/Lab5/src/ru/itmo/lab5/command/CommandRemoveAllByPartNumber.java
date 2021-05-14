package ru.itmo.lab5.command;

public class CommandRemoveAllByPartNumber extends AbstractCommand 
{
	public void execute(String... args) 
	{
		String partNumber = args[0];
		
		if (partNumber.length() > 0)
			commandHandler.getCollectionHandler().removeIf(e -> e.getPartNumber().equals(partNumber));
	}

	public String getInfo() 
	{
		return "removes all elements of collection whose partNumber equals entered partNumber";
	}

	public int argsCount() 
	{
		return 1;
	}
}
