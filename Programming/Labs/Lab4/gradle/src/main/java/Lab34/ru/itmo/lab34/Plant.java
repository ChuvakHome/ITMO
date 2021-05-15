package ru.itmo.lab34;

import java.util.ArrayList;
import java.util.List;

import ru.itmo.lab34.item.IPlantProduct;
import ru.itmo.lab34.world.Area;

public class Plant extends Thing
{
	private int height;
	private List<Sprout> sprouts = new ArrayList<Sprout>(); 
	
	public Plant(int height, String plantName, Area area)
	{
		super(plantName, area);
		
		this.height = height;
	}
	
	public void addSprout(Sprout sprout)
	{
		if (sprout != null)
			sprouts.add(sprout);
	}
	
	public List<Sprout> getSprouts()
	{
		return sprouts;
	}
	
	public List<IPlantProduct> getPlantProducts()
	{
		List<IPlantProduct> fruits = new ArrayList<IPlantProduct>();
		
		sprouts.forEach(e -> fruits.addAll(e.fruits));
		
		return fruits;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void wobble()
	{
		System.out.printf("Plant %s wobbles\n", name);
	}
	
	public class Sprout
	{
		private List<IPlantProduct> fruits = new ArrayList<IPlantProduct>();
		
		public Plant getPlant()
		{
			return Plant.this;
		}
		
		public void addPlantProduct(IPlantProduct fruit)
		{
			fruit.setSprout(this);
			fruits.add(fruit);
		}
		
		public void removePlantProduct(IPlantProduct fruit)
		{
			fruit.setSprout(null);
			fruits.remove(fruit);
		}
		
		public List<IPlantProduct> getPlantProducts()
		{
			return fruits;
		}
	}
}
