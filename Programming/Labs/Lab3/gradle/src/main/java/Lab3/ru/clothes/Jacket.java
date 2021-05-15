package ru.itmo.lab3.clothes;
import java.util.Objects;

public class Jacket extends UpperClothes
{
	public Jacket(Color c) 
	{
		super(c);
	}
	
	public boolean equals(Object o)
	{
		return o instanceof Jacket && Objects.equals(color, ((Jacket) o).color);
	}
	
	public String toString()
	{
		return "jacket: " + color.toString();
	}
}
