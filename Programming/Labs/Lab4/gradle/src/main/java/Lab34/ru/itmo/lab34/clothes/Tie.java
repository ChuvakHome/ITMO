package ru.itmo.lab34.clothes;

import java.util.Objects;

public class Tie extends UpperClothes
{
	public Tie(Color c) 
	{
		super(c);
	}

	public boolean equals(Object o)
	{
		return o instanceof Tie && Objects.equals(color, ((Tie) o).color);
	}
	
	public String toString()
	{
		return "Tie: " + color.toString();
	}
}
