package ru.itmo.lab3.item.food;

import ru.itmo.lab3.item.Item;

public class ItemFood extends Item implements Eatable
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
}
