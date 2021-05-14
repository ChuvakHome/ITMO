package ru.itmo.lab6.packet.client;

import ru.itmo.lab6.command.CollectionCommand;
import ru.itmo.lab6.command.Command.Args;
import ru.itmo.lab6.util.Constants;

public class PacketSendCollection extends ClientPacket
{
	private static final long serialVersionUID = Constants.SerialVersionUID.CLIENT_PACKET;
	
	private CollectionCommand collectionCommand;
	private Args args;
	
	public PacketSendCollection(CollectionCommand command, Args args)
	{
		this.collectionCommand = command;
		this.args = args;
	}
	
	public CollectionCommand getCollectionCommand()
	{
		return collectionCommand;
	}
	
	public Args getArgs()
	{
		return args;
	}
}
