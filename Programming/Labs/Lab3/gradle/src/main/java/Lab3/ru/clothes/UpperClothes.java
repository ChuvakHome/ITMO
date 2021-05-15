package ru.itmo.lab3.clothes;

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
