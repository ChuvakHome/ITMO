package ru.itmo.lab34.clothes;

public class UpperClothes extends ColouredClothes
{
	public UpperClothes(Color c)
	{
		super(c);
	}

	public ClothesType getType() 
	{
		return ClothesType.CLOTHES;
	}
}
