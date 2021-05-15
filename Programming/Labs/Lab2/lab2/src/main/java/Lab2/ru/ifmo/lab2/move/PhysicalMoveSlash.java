package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class PhysicalMoveSlash extends PhysicalMove
{
	public PhysicalMoveSlash()
	{
		super(Type.NORMAL, 70., 100.);
	}

	protected String describe()
	{
		return "использует Move Slash";
	}
}
