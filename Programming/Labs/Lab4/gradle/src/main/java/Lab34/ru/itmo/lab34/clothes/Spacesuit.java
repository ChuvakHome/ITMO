package ru.itmo.lab34.clothes;

import ru.itmo.lab34.IGasmask;

public abstract class Spacesuit extends UpperClothes implements IGasmask
{
	protected boolean wearing = false;
	
	public Spacesuit(boolean wearing) 
	{
		super(Color.WHITE);
		
		this.wearing = wearing;
	}
	
	public String toString()
	{
		return "White Spacesuit";
	}
	
	public void takeOn()
	{
		wearing = true;
	}
	
	public void takeOff()
	{
		wearing = false;
	}
	
	public boolean isWearing()
	{
		return wearing;
	}
}
