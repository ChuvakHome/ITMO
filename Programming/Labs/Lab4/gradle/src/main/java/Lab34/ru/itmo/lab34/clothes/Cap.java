package ru.itmo.lab34.clothes;

import java.util.Objects;

public class Cap extends Headwear 
{
	public Cap(Color c) 
	{
		super(c);
	}

	public boolean equals(Object o)
	{
		return o instanceof Cap && Objects.equals(color, ((Cap) o).color);
	}
	
	public String toString()
	{
		return "Cap: " + color.toString();
	}
}
