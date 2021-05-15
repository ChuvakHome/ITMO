package ru.itmo.lab34;

import ru.itmo.lab34.world.Area;

public class Fence extends Thing
{
	private Springtrap springtrap;
	
	public Fence(Area area) 
	{
		super("Fence", area);
		
		springtrap = new Springtrap(area);
	}

	public Springtrap getSpringtrap()
	{
		return springtrap;
	}
}
