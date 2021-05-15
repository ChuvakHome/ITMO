package ru.ifmo.lab2.pokemon;

import ru.ifmo.lab2.move.PhysicalMoveLowSweep;
import ru.ifmo.lab2.move.PhysicalMoveMetalClaw;
import ru.ifmo.lab2.move.PhysicalMoveSlash;
import ru.ifmo.se.pokemon.Type;

public class PokemonBisharp extends PokemonPawniard
{
	public PokemonBisharp(String name, int level)
	{
		super(name, level);
		setType(Type.DARK, Type.STEEL);
		setStats(65, 215, 100, 60, 70, 70);
		setMove(new PhysicalMoveMetalClaw(), new PhysicalMoveLowSweep(), new PhysicalMoveSlash());
	}
}
