package ru.itmo.lab3.clothes;

public class Shoes implements IClothes
{
	protected Color color;
	
	public Shoes(Color c)
	{
		color = c;
	}

	public Color getColor() 
	{
		return color;
	}

	public ClothesType getType() 
	{
		return ClothesType.SHOES;
	}
}
