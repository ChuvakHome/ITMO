package ru.itmo.lab34.item;

import ru.itmo.lab34.Plant;
import ru.itmo.lab34.item.food.Eatable;

public interface IPlantProduct extends Eatable
{
	void setSprout(Plant.Sprout sprout);
	
	Plant.Sprout getSprout();
}
