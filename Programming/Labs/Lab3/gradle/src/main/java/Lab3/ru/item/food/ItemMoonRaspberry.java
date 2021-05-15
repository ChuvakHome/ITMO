package ru.itmo.lab3.item.food;

public class ItemMoonRaspberry extends ItemMoonFood implements IBerry
{
	public ItemMoonRaspberry() 
	{
		super("Raspberry");
	}

	public int getSize() 
	{
		return 3;
	}
}
