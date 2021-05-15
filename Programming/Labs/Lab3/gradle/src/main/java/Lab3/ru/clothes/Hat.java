package ru.itmo.lab3.clothes;
import java.util.Objects;

public class Hat extends Headwear
{
	public Hat(Color c) 
	{
		super(c);
	}
	
	public boolean equals(Object o)
	{
		return o instanceof Hat && Objects.equals(color, ((Hat) o).color);
	}
	
	public String toString()
	{
		return "hat: " + color.toString();
	}
}
