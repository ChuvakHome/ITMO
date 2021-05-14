package ru.itmo.lab6.packet.client;

import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.util.Constants;

public class PacketExecuteCommand<E extends Command.Args> extends ClientPacket
{
	private static final long serialVersionUID = Constants.SerialVersionUID.CLIENT_PACKET;
	
	private Command command;
	private E args;
	
	public PacketExecuteCommand(Command command, E args)
	{
		this.command = command;
		this.args = args;
	}
	
	public Command getCommand()
	{
		return command;
	}
	
	public E getArgs()
	{
		return args;
	}
}
