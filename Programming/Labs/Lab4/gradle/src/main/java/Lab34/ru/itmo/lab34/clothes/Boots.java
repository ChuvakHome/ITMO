package ru.itmo.lab34.clothes;

import java.util.Objects;

import org.springframework.beans.factory.stereotype.Component;

@Component
public class Boots extends Shoes
{
	public Boots(Color c) 
	{
		super(c);
	}
	
	public Boots()
	{
		super(Color.BLACK);
	}

	public boolean equals(Object o)
	{
		return o instanceof Boots && Objects.equals(color, ((Boots) o).color);
	}
	
	public String toString()
	{
		return "Boots: " + color.toString();
	}
}
