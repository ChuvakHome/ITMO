package ru.itmo.lab3.item.food;

public class ItemSimpleRaspberry extends ItemFood implements IBerry 
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
