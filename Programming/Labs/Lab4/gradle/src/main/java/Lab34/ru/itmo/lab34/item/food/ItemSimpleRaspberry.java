package ru.itmo.lab34.item.food;

public class ItemSimpleRaspberry extends ItemFood
{
	public ItemSimpleRaspberry() 
	{
		super("Simple Raspberry", 10);
	}
	
	public int getSize()
	{
		return 5;
	}
}
