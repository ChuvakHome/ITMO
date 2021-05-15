package ru.itmo.lab34;

import ru.itmo.lab34.world.Area;

public class Thing
{
	protected String name;
	protected Area area;
	
	public Thing(String name, Area area)
	{
		this.name = name;
		setArea(area);
	}
	
	public void setArea(Area area)
	{
		this.area = area;
		
		if (!area.getThings().contains(this))
			this.area.addThings(this);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Area getArea()
	{
		return area;
	}
	
	public String getName()
	{
		return name;
	}
}
