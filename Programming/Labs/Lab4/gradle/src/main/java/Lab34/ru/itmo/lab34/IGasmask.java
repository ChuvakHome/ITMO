package ru.itmo.lab34;

import ru.itmo.lab34.clothes.IClothes;

public interface IGasmask extends IClothes
{
	void decrementBreathTimesCounter();
	
	int getBreathTimes();
	
	void takeOn();
	
	void takeOff();
	
	boolean isWearing();
}
