package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class StatusMoveWorkUp extends StatusMove
{
	public StatusMoveWorkUp()
	{
		super(Type.NORMAL, 0., 100.);
	}
	
	protected void applySelfEffects(Pokemon p)
	{
		p.setMod(Stat.ATTACK, 1);
		p.setMod(Stat.SPECIAL_ATTACK, 1);
	}
}
