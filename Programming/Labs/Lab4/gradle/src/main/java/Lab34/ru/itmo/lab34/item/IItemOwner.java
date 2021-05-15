package ru.itmo.lab34.item;

public interface IItemOwner 
{
	void takeItem(IItem item);
	
	void dropItem(IItem item);
	
	boolean hasItem(IItem item);
}
