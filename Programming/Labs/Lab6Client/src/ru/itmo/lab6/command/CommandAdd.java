package ru.itmo.lab6.command;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.util.Constants;

public class CommandAdd extends AbstractCommand<CommandAdd.Args>
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;

	public static final class Args extends Command.Args
	{
		private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND_ARGS;
		
		public final Product product;
		
		public Args(Product product)
		{
			this.product = product;
		}
	}

	public String getInfo() 
	{
		return "adds new {element} to collection";
	}
}
