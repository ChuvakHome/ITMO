package ru.itmo.lab7.command;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.util.Constants;

public class CommandRemoveLower extends AbstractServerCommand<CommandRemoveLower.Args>
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
	
	public void execute(Args args) 
	{
		serverCommandHandler.getCollectionHandler().getCollection().removeIf(e -> args.product.compareTo(e) > 0);
	}

	public String getInfo() 
	{
		return "removes all elements less than {element}";
	}
}
