package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class SpecialMoveThunder extends SpecialMove
{
	public SpecialMoveThunder()
	{
		super(Type.ELECTRIC, 110., 70.);
	}
	
	protected String describe()
	{
		return "использует Thunder";
	}
	
	protected void applyOppEffects(Pokemon p)
	{
		if (Math.random() < 0.3)
			Effect.paralyze(p);
	}
}
