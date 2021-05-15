package ru.itmo.lab34.item.food;

import ru.itmo.lab34.Plant.Sprout;
import ru.itmo.lab34.item.IPlantProduct;

public class ItemMoonTomato extends ItemMoonFood implements IPlantProduct
{
	private Sprout sprout;
	
	public ItemMoonTomato() 
	{
		this(null);
	}
	
	public ItemMoonTomato(Sprout sprout) 
	{
		super("Tomato");
		
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