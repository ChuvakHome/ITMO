package ru.itmo.lab6.command;

import java.util.ArrayDeque;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.packet.server.PacketCommandResult;
import ru.itmo.lab6.util.Constants;

public class CommandRemoveHead extends AbstractServerCommand<Command.Args>
{
	private static final long serialVersionUID = Constants.SerialVersionUID.COMMAND;
	
	public void execute(Args args) 
	{		
		ArrayDeque<Product> collection = serverCommandHandler.getCollectionHandler().getCollection();
		
		if (!collection.isEmpty())
		{
			Product head = collection.peek();
			collection.removeIf(product -> product.getId().equals(head.getId()));
			
			serverCommandHandler.getServer().sendPacket(new PacketCommandResult<Product>(this, head));
		}
	}
	
	public String getInfo() 
	{
		return "prints the first element of collection and removes the element";
	}
}
