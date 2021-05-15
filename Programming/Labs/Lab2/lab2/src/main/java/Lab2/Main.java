import ru.ifmo.lab2.pokemon.PokemonBisharp;
import ru.ifmo.lab2.pokemon.PokemonBuzzwole;
import ru.ifmo.lab2.pokemon.PokemonPawniard;
import ru.ifmo.lab2.pokemon.PokemonTogekiss;
import ru.ifmo.lab2.pokemon.PokemonTogepi;
import ru.ifmo.lab2.pokemon.PokemonTogetic;
import ru.ifmo.se.pokemon.Battle;

public class Main 
{
	public static void main(String[] args) 
	{
		PokemonBisharp p11 = new PokemonBisharp("Bisharp1", 35);
		PokemonPawniard p12 = new PokemonPawniard("Pawniard1", 35);
		PokemonTogepi p13 = new PokemonTogepi("Togepi1", 16);
		
		PokemonBuzzwole p21 = new PokemonBuzzwole("Buzzwole1", 1);
		PokemonTogekiss p22 = new PokemonTogekiss("Togekiss2", 1);
		PokemonTogetic p23 = new PokemonTogetic("Togetic2", 16);
		
		Battle b = new Battle();
		
		b.addAlly(p11);
		b.addAlly(p12);
		b.addAlly(p13);
		
		b.addFoe(p21);
		b.addFoe(p22);
		b.addFoe(p23);
		
		b.go();
	}
}
