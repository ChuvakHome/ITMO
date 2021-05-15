package ru.ifmo.lab2.pokemon;

import ru.ifmo.lab2.move.SpecialMoveAncientPower;
import ru.ifmo.lab2.move.StatusMoveDoubleTeam;
import ru.ifmo.lab2.move.StatusMoveSwagger;
import ru.ifmo.lab2.move.StatusMoveWorkUp;
import ru.ifmo.se.pokemon.Type;

public class PokemonTogekiss extends PokemonTogetic
{
	public PokemonTogekiss(String name, int level)
	{
		super(name, level);
		setType(Type.FAIRY, Type.FLYING);
		setStats(85, 50, 95, 120, 115, 80);
		setMove(new StatusMoveDoubleTeam(), new StatusMoveSwagger(), new SpecialMoveAncientPower(), new StatusMoveWorkUp());
	}
}
