package ru.itmo.lab34.exception;

import ru.itmo.lab34.person.Person;

public class PersonDeadException extends RuntimeException
{
	private static final long serialVersionUID = 4874886565807325429L;

	public PersonDeadException(Person p)
	{
		super(String.format("Person %s is dead", p.getName()));
	}
}
