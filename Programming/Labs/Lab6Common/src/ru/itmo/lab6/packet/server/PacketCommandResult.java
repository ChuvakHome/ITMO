package ru.itmo.lab6.packet.server;

import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.util.Constants;

public class PacketCommandResult<E> extends ServerPacket 
{
	private static final long serialVersionUID = Constants.SerialVersionUID.SERVER_PACKET;
	
	private Command command;
	private E result;
	
	public PacketCommandResult(Command command, E result)
	{
		this.command = command;
		this.result = result;
	}
	
	public Command getCommand()
	{
		return command;
	}
	
	public E getResult()
	{
		return result;
	}
}
