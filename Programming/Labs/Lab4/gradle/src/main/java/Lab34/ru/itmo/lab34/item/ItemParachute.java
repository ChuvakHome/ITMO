package ru.itmo.lab34.item;

import ru.itmo.lab34.IParachute;

public class ItemParachute extends Item implements IParachute
{
	public ItemParachute() 
	{
		super("Parachute");
	}

	public double getDecelerationFactor() 
	{
		return 0.5;
	}
}
