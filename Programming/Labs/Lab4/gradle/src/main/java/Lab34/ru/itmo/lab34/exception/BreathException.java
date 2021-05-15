package ru.itmo.lab34.exception;

import ru.itmo.lab34.person.Person;
import ru.itmo.lab34.world.Area;

public class BreathException extends Exception
{
	private static final long serialVersionUID = -370607331204401494L;

	public BreathException(Person p)
	{
		super(String.format("Person %s can't breath", p.getName()));
	}
	
	public BreathException(Person p, Area w)
	{
		super(String.format("Person %s can't breath in world %s", p.getName(), w.getWorldName()));
	}
}
