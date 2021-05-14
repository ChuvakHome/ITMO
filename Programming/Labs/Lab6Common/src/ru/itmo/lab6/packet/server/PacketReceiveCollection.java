package ru.itmo.lab6.packet.server;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.command.CollectionCommand;
import ru.itmo.lab6.command.Command.Args;
import ru.itmo.lab6.util.Constants;

public class PacketReceiveCollection extends ServerPacket
{
	private static final long serialVersionUID = Constants.SerialVersionUID.SERVER_PACKET;
	
	private CollectionCommand collectionCommand;
	private Args args;
	private Product[] products;
	
	public PacketReceiveCollection(CollectionCommand command, Args args, Product[] products)
	{
		this.collectionCommand = command;
		this.args = args;
		this.products = products;
	}
	
	public CollectionCommand getCollectionCommand()
	{
		return collectionCommand;
	}
	
	public Args getArgs()
	{
		return args;
	}
	
	public Product[] getProducts()
	{
		return products;
	}
}
