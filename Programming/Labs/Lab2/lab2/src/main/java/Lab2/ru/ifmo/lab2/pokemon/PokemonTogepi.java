package ru.ifmo.lab2.pokemon;

import ru.ifmo.lab2.move.StatusMoveDoubleTeam;
import ru.ifmo.lab2.move.StatusMoveSwagger;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class PokemonTogepi extends Pokemon
{
	public PokemonTogepi(String name, int level) 
	{
		super(name, level);
		setType(Type.FAIRY);
		setStats(35, 20, 65, 40, 65, 20);
		setMove(new StatusMoveDoubleTeam(), new StatusMoveSwagger());
	}
}
