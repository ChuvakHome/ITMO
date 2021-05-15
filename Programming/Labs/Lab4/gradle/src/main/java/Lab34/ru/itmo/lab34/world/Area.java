package ru.itmo.lab34.world;

import java.util.ArrayList;
import java.util.List;

import ru.itmo.lab34.Thing;

public abstract class Area 
{
	private String worldName;
	private double gravityAcceleration;
	
	private List<Thing> thingsList = new ArrayList<Thing>();
	
	public Area(String worldName, double gravityAcceleration)
	{
		this.worldName = worldName;
		this.gravityAcceleration = gravityAcceleration;
	}
	
	public abstract boolean hasAir();
	
	public String getWorldName()
	{
		return worldName;
	}
	
	public double getGravityAcceleration()
	{
		return gravityAcceleration;
	}
	
	public final String toString()
	{
		return String.format("World %s:\n\tGravity Acceleration: %.2f m/s^2\n\t-Has Air: %s", worldName, gravityAcceleration, hasAir() ? "yes" : "no");
	}
	
	public void addThings(Thing... things)
	{
		for (Thing thing: things)
		{
			if (!thingsList.contains(thing))
				thingsList.add(thing);
				
			if (thing.getArea() == null || !thing.getArea().equals(this))
				thing.setArea(this);
		}
	}
	
	public List<Thing> getThings()
	{
		return thingsList;
	}
}
