package ru.itmo.lab34.item.food;

import ru.itmo.lab34.Plant.Sprout;
import ru.itmo.lab34.item.IPlantProduct;

public class ItemMoonPear extends ItemMoonFood implements IPlantProduct
{
	private Sprout sprout;
	
	public ItemMoonPear() 
	{
		this(null);
	}
	
	public ItemMoonPear(Sprout spout) 
	{
		super("Pear");
		
		this.sprout = spout;
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
