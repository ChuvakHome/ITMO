package ru.itmo.lab6.command;

import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Validation;
import ru.itmo.lab6.util.Validators;

public class CommandRemoveById extends AbstractServerCommand<CommandRemoveById.Args> 
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public static final class Args extends Command.Args
	{
		private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND_ARGS;
		
		@Validation(Validators.PositiveIntegerValidator.class)
		public final int id;
		
		public Args(int id)
		{
			this.id = id;
		}
	}
	
	public void execute(Args args) 
	{	
		serverCommandHandler.getCollectionHandler().removeIf(product -> product.getId().equals(args.id));
	}

	public String getInfo() 
	{
		return "removes element with entered id";
	}
}
