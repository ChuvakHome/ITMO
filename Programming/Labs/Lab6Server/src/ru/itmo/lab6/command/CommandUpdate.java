package ru.itmo.lab6.command;

import java.util.Collection;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Validation;
import ru.itmo.lab6.util.Validators;

public class CommandUpdate extends AbstractServerCommand<CommandUpdate.Args> 
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
	
	public void execute(Args args)
	{
		Collection<Product> collection = serverCommandHandler.getCollectionHandler().getCollection();

		if (collection.stream().noneMatch(p -> p.getId().equals(args.id)))
			return;
		
		args.product.setId(args.id);
		
		collection.removeIf(e -> e.getId().equals(args.id));
		collection.add(args.product);
	}

	public String getInfo() 
	{
		return "updates element of collection with {id}";
	}
}
