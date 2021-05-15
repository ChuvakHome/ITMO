package ru.itmo.lab3.clothes;
import java.util.Objects;

public class Sandals extends Shoes
{
	public Sandals(Color c)
	{
		super(c);
	}
	
	public boolean equals(Object o)
	{		
		return o instanceof Sandals && Objects.equals(color, ((Sandals) o).color);
	}
	
	public String toString()
	{
		return "sandals: " + color.toString();
	}
}
