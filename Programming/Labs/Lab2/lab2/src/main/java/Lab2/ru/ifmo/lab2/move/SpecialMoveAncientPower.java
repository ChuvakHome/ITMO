package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class SpecialMoveAncientPower extends SpecialMove
{
	public SpecialMoveAncientPower()
	{
		super(Type.ROCK, 60., 100.);
	}
	
	protected String describe()
	{
		return "использует Ancient Power";
	}
	
	protected void applySelfEffects(Pokemon p)
	{
		if (Math.random() < 0.1)
		{
			p.setMod(Stat.ATTACK, 1);
			p.setMod(Stat.DEFENSE, 1);
			p.setMod(Stat.SPECIAL_ATTACK, 1);
			p.setMod(Stat.SPECIAL_DEFENSE, 1);
		}
	}
}
