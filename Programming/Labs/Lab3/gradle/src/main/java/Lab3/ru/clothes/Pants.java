package ru.itmo.lab3.clothes;

public class Pants extends ColouredClothes
{
	public Pants(Color c)
	{
		super(c);
	}

	public ClothesType getType() 
	{
		return ClothesType.PANTS;
	}
}
