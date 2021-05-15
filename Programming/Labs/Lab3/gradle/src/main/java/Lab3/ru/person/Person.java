package ru.itmo.lab3.person;
import java.util.Objects;

import ru.itmo.lab3.clothes.IClothes;
import ru.itmo.lab3.item.IItem;
import ru.itmo.lab3.item.IItemOwner;
import ru.itmo.lab3.item.Item;
import ru.itmo.lab3.item.food.Eatable;

public class Person implements IItemOwner
{
	private String name;
		
	protected IClothes[] clothes;
	protected IItem[] items;
	
	private int satiety;
	
	public Person(String name)
	{
		this.name = name;
	}
	
	public Person(String name, IClothes... clothes)
	{
		this.name = name;
		this.clothes = clothes;
	}
	
	public Person(String name, Item... items)
	{
		this.name = name;
		this.items = items;
	}
	
	public void setClothes(IClothes... clothes)
	{
		this.clothes = clothes;
	}
	
	public void setItems(IItem... items)
	{
		this.items = items;
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof Person)
		{
			Person p = (Person) o;
		
			return Objects.equals(name, p.name) && Objects.deepEquals(clothes, p.clothes) && Objects.deepEquals(items, p.items);
		}
		else
			return false;
	}
	
	public int hashCode()
	{
		return Objects.hash(name, clothes);
	}
	
	public String toString()
	{
		String s = String.format("Person %s", name);
		
		if (clothes != null)
		{
			s += "\n\tClothes:";
			
			for (int i = 0; i < clothes.length; ++i)
				s += "\n\t\t-" + clothes[i].toString(); 
		}
		
		if (items != null)
		{
			s += "\n\tItems:";
			
			for (int i = 0; i < items.length; ++i)
			{
				if (items[i] != null)
					s += "\n\t\t-" + items[i].toString();
			}
		}
		
		return s;
	}
	
	public void searchForFood()
	{		
		while (Math.random() >= 0.7)
		{
			if (satiety == 0)
				System.out.printf("%sI'm so hungry! ", name != null && name.isEmpty() ? "" : name + ": ");
			
			System.out.println("I'm searching for food!");
		}
		
		System.out.println("Yeah! I've found a food!");
	}
	
	public void takeItem(IItem item)
	{
		if (items == null)
		{
			items = new IItem[1];
			items[0] = item;
		}
		else
		{
			IItem[] temp = new IItem[items.length + 1];
			
			for (int i = 0; i < items.length; ++i)
				temp[i] = items[i];
			
			temp[items.length] = item;
			
			items = temp;
		}
	}
	
	public void eat(Eatable eatable)
	{
		if (!hasItem(eatable))
			takeItem(eatable);
		
		for (int i = 0; i < items.length; ++i)
		{
			if (Objects.equals(eatable, items[i]))
			{
				satiety += eatable.getSatiety();
				items[i] = null;
				return;
			}
		}
	}
	
	public void dropItem(IItem item)
	{
		for (int i = 0; i < items.length; ++i)
		{
			if (Objects.equals(item, items[i]))
			{
				items[i] = null;
				return;
			}
		}
	}
	
	public boolean hasItem(IItem item)
	{
		for (int i = 0; i < items.length; ++i)
		{
			if (Objects.equals(item, items[i]))
				return true;
		}
		
		return false;
	}
}
