package ru.ifmo.lab2.pokemon;

import ru.ifmo.lab2.move.SpecialMoveAncientPower;
import ru.ifmo.lab2.move.StatusMoveDoubleTeam;
import ru.ifmo.lab2.move.StatusMoveSwagger;
import ru.ifmo.se.pokemon.Type;

public class PokemonTogetic extends PokemonTogepi
{
	public PokemonTogetic(String name, int level) 
	{
		super(name, level);
		setType(Type.FAIRY, Type.FLYING);
		setStats(55, 40, 85, 80, 105, 40);
		setMove(new StatusMoveDoubleTeam(), new StatusMoveSwagger(), new SpecialMoveAncientPower());
	}
}
