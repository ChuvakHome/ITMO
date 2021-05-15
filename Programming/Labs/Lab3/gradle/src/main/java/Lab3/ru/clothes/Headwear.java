package ru.itmo.lab3.clothes;

public class Headwear extends ColouredClothes
{
	public Headwear(Color c) 
	{
		super(c);
	}

	public ClothesType getType() 
	{
		return ClothesType.HEADWEAR;
	}
}
