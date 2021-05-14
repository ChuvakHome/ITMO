package ru.itmo.lab6.command;

import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Validation;
import ru.itmo.lab6.util.Validators;

public class CommandRemoveAllByPartNumber extends AbstractServerCommand<CommandRemoveAllByPartNumber.Args> 
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public static final class Args extends Command.Args
	{
		private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND_ARGS;
		
		@Validation(Validators.NotEmptyStringValidator.class)
		public final String partNumber;
		
		public Args(String partNumber)
		{
			this.partNumber = partNumber;
		}
	}
	
	public void execute(Args args) 
	{
		if (args.partNumber.length() > 0)
			serverCommandHandler.getCollectionHandler().removeIf(e -> e.getPartNumber().equals(args.partNumber));
	}

	public String getInfo() 
	{
		return "removes all elements of collection whose partNumber equals entered partNumber";
	}

	public int standardArgsCount() 
	{
		return 1;
	}
}
