package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class StatusMoveSwagger extends StatusMove
{
	public StatusMoveSwagger()
	{
		super(Type.NORMAL, 0., 85.);
	}
	
	protected void setOppEffects(Pokemon p)
	{
		p.confuse();
		p.setMod(Stat.ATTACK, 2);
	}
}
