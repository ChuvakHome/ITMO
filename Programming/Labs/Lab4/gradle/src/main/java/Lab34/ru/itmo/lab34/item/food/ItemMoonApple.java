package ru.itmo.lab34.item.food;

import ru.itmo.lab34.Plant.Sprout;
import ru.itmo.lab34.item.IPlantProduct;

public class ItemMoonApple extends ItemMoonFood implements IPlantProduct
{
	private Sprout sprout;
	
	public ItemMoonApple() 
	{
		this(null);
	}
	
	public ItemMoonApple(Sprout sprout) 
	{
		super("Apple");
		
		this.sprout = sprout;
	}

	public int getSize() 
	{
		return 2;
	}
	
	public void setSprout(Sprout sprout)
	{
		this.sprout = sprout;
	}
	
	public Sprout getSprout()
	{
		return sprout;
	}
}
