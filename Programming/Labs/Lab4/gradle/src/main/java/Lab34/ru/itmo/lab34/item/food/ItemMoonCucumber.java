package ru.itmo.lab34.item.food;

import ru.itmo.lab34.Plant.Sprout;
import ru.itmo.lab34.item.IPlantProduct;

public class ItemMoonCucumber extends ItemMoonFood implements IPlantProduct
{
	private Sprout sprout;
	
	public ItemMoonCucumber() 
	{
		this(null);
	}
	
	public ItemMoonCucumber(Sprout sprout) 
	{
		super("Cucumber");
		
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
