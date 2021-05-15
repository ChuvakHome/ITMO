package ru.ifmo.lab2.pokemon;

import ru.ifmo.lab2.move.PhysicalMoveLowSweep;
import ru.ifmo.lab2.move.PhysicalMoveMetalClaw;
import ru.ifmo.lab2.move.PhysicalMoveSlash;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class PokemonPawniard extends Pokemon
{
	public PokemonPawniard(String name, int level)
	{
		super(name, level);
		setType(Type.DARK, Type.STEEL);
		setStats(45, 85, 70, 40, 40, 60);
		setMove(new PhysicalMoveMetalClaw(), new PhysicalMoveLowSweep(), new PhysicalMoveSlash());
	}
}
