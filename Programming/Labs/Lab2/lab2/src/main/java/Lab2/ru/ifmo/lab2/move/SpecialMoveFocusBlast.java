package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class SpecialMoveFocusBlast extends SpecialMove
{
	public SpecialMoveFocusBlast()
	{
		super(Type.FIGHTING, 120., 100.);
	}
	
	protected String describe()
	{
		return "использует Focust Blast";
	}
	
	protected void applyOppEffects(Pokemon p)
	{
		if (Math.random() < 0.1)
			p.setMod(Stat.SPECIAL_DEFENSE, 1);
	}
}
