package ru.itmo.lab34.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ru.itmo.lab34.Breakable;
import ru.itmo.lab34.IDamageSource;
import ru.itmo.lab34.IGasmask;
import ru.itmo.lab34.IParachute;
import ru.itmo.lab34.Thing;
import ru.itmo.lab34.clothes.IClothes;
import ru.itmo.lab34.exception.BreathException;
import ru.itmo.lab34.exception.PersonDeadException;
import ru.itmo.lab34.item.IItem;
import ru.itmo.lab34.item.IItemOwner;
import ru.itmo.lab34.item.IPlantProduct;
import ru.itmo.lab34.item.food.Eatable;
import ru.itmo.lab34.world.Area;

public class Person extends Thing implements IItemOwner, IDamageSource
{
	private String name;
	
	private IItem activeItem;
	
	private boolean isAlive = true;
	
	private int height;
	private int health = 100;
	
	protected List<IClothes> clothes = new ArrayList<IClothes>();
	protected List<IItem> items = new ArrayList<IItem>();
	
	private int satiety;
	
	public Person(String name, int height, Area world)
	{
		this(name, height, world, new IItem[0], new IClothes[0]);
	}
	
	public Person(String name, int height, Area world, IClothes... clothes)
	{
		this(name, height, world, new IItem[0], clothes);
	}
	
	public Person(String name, int height, Area world, IItem... items)
	{
		this(name, height, world, items, new IClothes[0]);
	}
	
	public Person(String name, int height, Area area, IItem[] items, IClothes[] clothes)
	{
		super(name, area);
		
		this.name = name;
		this.height = height;
		this.area = area;
		this.items = new ArrayList<IItem>(Arrays.asList(items));
		this.clothes = new ArrayList<IClothes>(Arrays.asList(clothes));
	}
	
	public void setClothes(IClothes... clothes)
	{
		for (IClothes iClothes: clothes)
			this.clothes.add(iClothes);
	}
	
	public void addItems(IItem... items)
	{
		for (IItem item: items)
			this.items.add(item);
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof Person)
		{
			Person p = (Person) o;
		
			return Objects.equals(name, p.name) && Objects.deepEquals(clothes, p.clothes) && Objects.deepEquals(items, p.items);
		}
		else
			return false;
	}
	
	public int hashCode()
	{
		return Objects.hash(name, clothes);
	}
	
	public String toString()
	{
		String s = String.format("Person %s", name);
		
		if (area != null)
			s += String.format("\n\tWorld: %s\n\t\tGravity Aceleration: %.2f m/s^2\n\t\tHas Air: %s", 
					area.getWorldName(), area.getGravityAcceleration(), area.hasAir() ? "Yes" : "No");
		
		if (clothes != null)
		{
			s += "\n\tClothes:";
			
			for (IClothes iClothes: clothes)
			{
				if (iClothes != null)
					s += "\n\t\t" + Objects.toString(iClothes);
			}
		}
		
		if (items != null)
		{
			s += "\n\tItems:";
			
			for (IItem item: items)
			{
				if (item != null)
					s += "\n\t\t" + Objects.toString(item);
			}
		}
		
		return s;
	}
	
	public void setActiveItem(IItem item)
	{
		if (hasItem(item))
			activeItem = item;
	}
	
	public void breath() throws BreathException
	{
		if (isAlive)
		{
			if (!area.hasAir())
			{
				IGasmask gasmask = getGasmask();
				
				if (gasmask != null && gasmask.getBreathTimes() > 0)
					gasmask.decrementBreathTimesCounter();
				else
					throw new BreathException(this, area);
			}
		}
		else
			throw new PersonDeadException(this);
	}
	
	public void takeOnGasmask()
	{
		IGasmask gasmask = getGasmask();
		
		if (gasmask != null)
		{
			gasmask.takeOn();
			System.out.printf("%s took on %s\n", name, gasmask.toString());
		}
	}
	
	public void takeOffGasmask()
	{
		IGasmask gasmask = getGasmask();
		
		if (gasmask != null)
		{
			gasmask.takeOff();
			System.out.printf("%s took on %s\n", name, gasmask.toString());
		}
	}
	
	public IGasmask getGasmask()
	{
		for (IClothes iClothes: clothes)
		{
			if (iClothes instanceof IGasmask)
				return (IGasmask) iClothes;
		}
		
		for (IItem item: items)
		{
			if (item instanceof IGasmask)
				return (IGasmask) item;
		}
		
		return null;
	}
	
	public IParachute getParachute()
	{
		for (IItem item: items)
		{
			if (item instanceof IParachute)
				return (IParachute) item;
		}
		
		return null;
	}
	
	public void searchForFood()
	{		
		while (Math.random() >= 0.7)
		{
			if (satiety == 0)
				say("I'm so hungry! I'm searching for food...");
		}
		
		say("Yeah! I've found a food!");
	}
	
	public boolean isAlive()
	{
		return isAlive;
	}
	
	public void die()
	{
		isAlive = false;
		System.out.println(name + " dead");
	}
	
	public void say(String words, Object... args)
	{
		if (isAlive)
			System.out.printf(name != null && !name.isEmpty() ? name + ": " + String.format(words, args) + "\n": "");
	}
	
	public void takeItem(IItem item)
	{
		items.add(item);
	}
	
	public void eat(Eatable eatable)
	{
		if (!hasItem(eatable))
		{
			takeItem(eatable);
			
			if (eatable instanceof IPlantProduct)
				((IPlantProduct) eatable).getSprout().removePlantProduct((IPlantProduct) eatable);
		}
		
		IItem item = null;
		
		for (IItem e: items)
		{
			if (Objects.equals(eatable, e))
			{
				item = e;
				break;
			}
		}
		
		if (item != null)
		{
			System.out.printf("%s is eating %s\n", name, eatable.getName());
			satiety += eatable.getSatiety();
			items.remove(item);
		}
	}
	
	public void dropItem(IItem item)
	{
		items.remove(item);
	}
	
	public boolean hasItem(IItem item)
	{
		return items.contains(item);
	}
	
	public void goTo(Thing thing)
	{
		if (thing != null && thing.getArea().equals(area))
			System.out.printf("%s is going to %s\n", name, thing.getName());
	}
	
	public void hit(Person p)
	{
		if (activeItem != null)
			System.out.printf("%s hit %s with %s\n", name, p.name, activeItem.getName());
		else
			System.out.printf("%s hit %s\n", name, p.name);
		
		p.damaged(this);
	}
	
	public void lookAt(Thing thing)
	{
		System.out.printf("%s is looking at %s\n", name, thing.getName());
	}
	
	public void breakThing(Breakable breakable)
	{
		breakable.breaking(this);
	}
	
	public void damaged(IDamageSource iDamagedSource)
	{
		ouch();
		health -= iDamagedSource.getDamage();
		
		if (health <= 0)
		{
			health = 0;
			die();
		}
	}
	
	public void ouch()
	{
		say("Ouch!");
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getDamage()
	{
		return 10;
	}
	
	public void wander()
	{
		System.out.printf("%s is wandering\n", name);
	}
}
