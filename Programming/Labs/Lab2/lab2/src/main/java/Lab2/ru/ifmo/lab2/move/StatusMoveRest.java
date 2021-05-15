package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class StatusMoveRest extends StatusMove
{
	public StatusMoveRest()
	{
		super(Type.PSYCHIC, 0., 100.);
	}
	
	protected void applySelfEffect(Pokemon p)
	{
		p.setCondition((new Effect()).condition(Status.SLEEP).attack(0.0D).turns(2));
		p.setMod(Stat.HP, (int) (p.getHP() - p.getStat(Stat.HP)));
	}
}
