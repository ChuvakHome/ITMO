package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.Type;

public class PhysicalMoveFacade extends PhysicalMove
{
	public PhysicalMoveFacade()
	{
		super(Type.NORMAL, 70., 100.);
	}
	
	protected String describe()
	{
		return "использует Facade";
	}
	
	protected void applyOppDamage(Pokemon p, double damage)
	{
		if (p.getCondition() == Status.POISON || p.getCondition() == Status.PARALYZE)
			p.setMod(Stat.HP, (int) (damage + 2 * power));
		else if (p.getCondition() == Status.BURN)
			p.setMod(Stat.HP, (int) (damage + power));
	}
}
