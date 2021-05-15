package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class StatusMoveDoubleTeam extends StatusMove
{
	public StatusMoveDoubleTeam()
	{
		super(Type.NORMAL, 0., 100.);
	}
	
	protected void applySelfEffects(Pokemon p)
	{
		p.setMod(Stat.EVASION, 1);
	}
}
