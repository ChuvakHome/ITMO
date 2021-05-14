package ru.itmo.lab6.command;

import java.util.ArrayDeque;

import ru.itmo.lab6.collection.Product;

public abstract class AbstractCollectionCommand<E extends Command.Args> extends AbstractCommand<E> implements CollectionCommand 
{
	private static final long serialVersionUID = 1L;
	
	protected ArrayDeque<Product> collection;
	
	public AbstractCollectionCommand()
	{
		super();
	}
	
	public AbstractCollectionCommand(String name)
	{
		super(name);
	}
	
	public void setCollection(ArrayDeque<Product> deque)
	{
		collection = deque;
	}
	
	public CommandType getCommandType()
	{
		return CommandType.COLLECTION_COMMAND;
	}
}
