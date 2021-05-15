package ru.itmo.lab34.item;

import java.util.Objects;

public class Item implements IItem
{
	protected String name;
	
	public Item(String name)
	{
		this.name = name;
	}
	
	public boolean equals(Object o)
	{
		return o instanceof Item && Objects.equals(name, ((Item) o).name);
	}
	
	public int hashCode()
	{
		return Objects.hash(name);
	}
	
	public String toString()
	{
		return "Item " + name;
	}
	
	public String getName()
	{
		return name;
	}
}
