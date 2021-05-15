package ru.itmo.lab34.item.food;

import ru.itmo.lab34.item.Item;

public abstract class ItemFood extends Item implements Eatable
{
	protected int satiety;
	
	public ItemFood(String name, int satiety) 
	{
		super(name);
		
		this.satiety = satiety;
	}
	
	public void setSatiety(int satiety)
	{
		this.satiety = satiety;
	}
	
	public int getSatiety()
	{
		return satiety;
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof ItemFood)
		{
			ItemFood itemFood = (ItemFood) o;
			
			return itemFood.name == name && itemFood.satiety == satiety;
		}
		
		return false;
	}
	
	public String toString()
	{
		return String.format("%s: satiety = %d", name, satiety);
	}
	
	public abstract int getSize();
}
