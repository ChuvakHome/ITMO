package ru.itmo.lab34.item.food;

import ru.itmo.lab34.Plant.Sprout;
import ru.itmo.lab34.item.IPlantProduct;

public class ItemMoonRaspberry extends ItemMoonFood implements IPlantProduct
{
	private Sprout sprout;
	
	public ItemMoonRaspberry() 
	{
		this(null);
	}
	
	public ItemMoonRaspberry(Sprout sprout) 
	{
		super("Raspberry");
		
		this.sprout = sprout;
	}

	public int getSize() 
	{
		return 3;
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
