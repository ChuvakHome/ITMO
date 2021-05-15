package ru.ifmo.lab2.pokemon;

import ru.ifmo.lab2.move.PhysicalMoveFacade;
import ru.ifmo.lab2.move.SpecialMoveThunder;
import ru.ifmo.lab2.move.StatusMoveDoubleTeam;
import ru.ifmo.lab2.move.StatusMoveRest;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class PokemonBuzzwole extends Pokemon
{
	public PokemonBuzzwole(String name, int level)
	{
		super(name, level);
		setType(Type.BUG, Type.FIGHTING);
		setStats(107, 139, 139, 53, 53, 79);
		setMove(new PhysicalMoveFacade(), new StatusMoveDoubleTeam(), new SpecialMoveThunder(), new StatusMoveRest());
	}
}
