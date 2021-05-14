package ru.itmo.lab6.command;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Validation;
import ru.itmo.lab6.util.Validators;

public class CommandUpdate extends AbstractCommand<CommandUpdate.Args> 
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public static final class Args extends Command.Args
	{
		private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND_ARGS;
		
		@Validation(Validators.PositiveIntegerValidator.class)
		public final int id;
		
		public final Product product;
		
		public Args(int id, Product product)
		{
			this.id = id;
			this.product = product;
		}
	}

	public String getInfo() 
	{
		return "updates element of collection with {id}";
	}
}
