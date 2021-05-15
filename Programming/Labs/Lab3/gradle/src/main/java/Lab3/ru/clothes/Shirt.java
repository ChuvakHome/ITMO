package ru.itmo.lab3.clothes;
import java.util.Objects;

public class Shirt extends UpperClothes
{
	public Shirt(Color c) 
	{
		super(c);
	}
	
	public boolean equals(Object o)
	{
		return o instanceof Shirt && Objects.equals(color, ((Shirt) o).color);
	}
	
	public String toString()
	{
		return "shirt: " + color.toString();
	}
}
