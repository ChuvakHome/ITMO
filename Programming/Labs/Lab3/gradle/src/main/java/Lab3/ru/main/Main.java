package ru.itmo.lab3.main;

import org.springframework.beans.factory.BeanFactory;

import ru.itmo.lab3.clothes.Boots;
import ru.itmo.lab3.clothes.Cap;
import ru.itmo.lab3.clothes.Color;
import ru.itmo.lab3.clothes.Hat;
import ru.itmo.lab3.clothes.Jacket;
import ru.itmo.lab3.clothes.Sandals;
import ru.itmo.lab3.clothes.Shirt;
import ru.itmo.lab3.clothes.Tie;
import ru.itmo.lab3.clothes.Trousers;
import ru.itmo.lab3.item.Item;
import ru.itmo.lab3.item.food.ItemMoonFood;
import ru.itmo.lab3.item.food.ItemMoonRaspberry;
import ru.itmo.lab3.person.Person;

public class Main {

	public static void main(String[] args)
	{	
		BeanFactory beanFactory = new BeanFactory();
		beanFactory.instantinate("ru.itmo.lab3.clothes");
		
		Person personNeznaika = new Person("Neznaika", new Hat(Color.BLUE), new Shirt(Color.RED), 
				new Tie(Color.GREEN), new Trousers(Color.YELLOW), (Boots) beanFactory.getBean("boots"));
		
		ItemMoonFood moonPear = new ItemMoonFood("Pear");
		
		personNeznaika.setItems(moonPear);
		
		personNeznaika.dropItem(moonPear);
		
		personNeznaika.searchForFood();
		
		personNeznaika.eat(new ItemMoonRaspberry());
		
		Person personFix = new Person("Fix", new Cap(Color.RED), new Jacket(Color.RED), new Sandals(Color.RED));
		personFix.setItems(new Item("Broom"));
		
		System.out.println(personNeznaika);
		System.out.println(personFix);
	}
}
