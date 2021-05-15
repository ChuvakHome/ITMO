package ru.itmo.lab34;

import ru.itmo.lab34.person.Person;
import ru.itmo.lab34.world.Area;

public class Springtrap extends Thing implements IDamageSource, Breakable
{
	private Spring spring = new Spring();
	
	public Springtrap(Area area) 
	{
		super("Spring Trap", area);
	}
	
	public void springTrap(Person p)
	{
		spring.pressed = true;
		
		p.damaged(this);
	}
	
	public void breaking(Person p)
	{
		System.out.printf("%s broke %s\n", p.getName(), name);
		
		spring.broken = true;
	}
	
	public int getDamage()
	{
		return 40;
	}

	public static class Spring
	{
		private boolean broken; 
		private boolean pressed;
		
		public void breakSpring()
		{
			broken = true;
			pressed = false;
		}
		
		public boolean isBroken()
		{
			return broken;
		}
		
		public boolean isPressed()
		{
			return pressed;
		}
	}
}
