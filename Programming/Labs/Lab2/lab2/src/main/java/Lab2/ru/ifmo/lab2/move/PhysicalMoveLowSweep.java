package ru.ifmo.lab2.move;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class PhysicalMoveLowSweep extends PhysicalMove
{
	public PhysicalMoveLowSweep()
	{
		super(Type.FIGHTING, 65., 100.);
	}
	
	protected String describe()
	{
		return "использует Low Sweep";
	}
	
	protected void applyOppEffects(Pokemon p)
	{
		p.setMod(Stat.SPEED, 1);
	}
}
