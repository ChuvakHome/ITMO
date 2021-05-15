package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class PhysicalMoveMetalClaw extends PhysicalMove
{
	public PhysicalMoveMetalClaw()
	{
		super(Type.STEEL, 50., 95.);
	}
	
	protected String describe()
	{
		return "использует Metal Claw";
	}
	
	protected void applySelfEffects(Pokemon p)
	{
		if (Math.random() < 0.1)
			p.setMod(Stat.ATTACK, 1);
	}
}
