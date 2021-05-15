package ru.itmo.lab34.clothes;

import java.util.Objects;

public class Trousers extends Pants
{
	public Trousers(Color c) 
	{
		super(c);
	}

	public boolean equals(Object o)
	{
		if (o instanceof Jacket)
			return Objects.equals(color, ((Jacket) o).getColor());
		else
			return false;
	}
	
	public String toString()
	{
		return "Trousers: " + color.toString();
	}
}
