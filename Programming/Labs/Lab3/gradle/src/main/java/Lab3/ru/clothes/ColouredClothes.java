package ru.itmo.lab3.clothes;
import java.util.Objects;

public abstract class ColouredClothes implements IClothes
{
	protected Color color;
	
	public ColouredClothes(Color c)
	{
		color = c;
	}
	
	public IColor getColor() 
	{
		return color;
	}
	
	public int hashCode()
	{
		return Objects.hash(getType(), color);
	}
}
