package ru.itmo.lab34.main;

import org.springframework.beans.factory.BeanFactory;

import ru.itmo.lab34.Fence;
import ru.itmo.lab34.Plant;
import ru.itmo.lab34.Plant.Sprout;
import ru.itmo.lab34.clothes.Boots;
import ru.itmo.lab34.clothes.Cap;
import ru.itmo.lab34.clothes.Color;
import ru.itmo.lab34.clothes.Hat;
import ru.itmo.lab34.clothes.Jacket;
import ru.itmo.lab34.clothes.Sandals;
import ru.itmo.lab34.clothes.Shirt;
import ru.itmo.lab34.clothes.Spacesuit;
import ru.itmo.lab34.clothes.Tie;
import ru.itmo.lab34.clothes.Trousers;
import ru.itmo.lab34.exception.BreathException;
import ru.itmo.lab34.exception.PersonDeadException;
import ru.itmo.lab34.item.Item;
import ru.itmo.lab34.item.ItemParachute;
import ru.itmo.lab34.item.food.ItemMoonApple;
import ru.itmo.lab34.item.food.ItemMoonCucumber;
import ru.itmo.lab34.item.food.ItemMoonPear;
import ru.itmo.lab34.item.food.ItemMoonRaspberry;
import ru.itmo.lab34.item.food.ItemMoonTomato;
import ru.itmo.lab34.person.Person;
import ru.itmo.lab34.world.Area;

public class Main 
{
	public static void main(String[] args)
	{
		class MoonArea extends Area
		{
			public MoonArea() 
			{
				super("Moon", 1.62);
			}

			public boolean hasAir() 
			{
				return true;
			}
		}
		
		class MoonAtmosphereArea extends Area
		{
			public MoonAtmosphereArea() 
			{
				super("Moon Atmosphere", 1.62);
			}

			public boolean hasAir() 
			{
				return false;
			}
		}
		
		BeanFactory beanFactory = new BeanFactory();
		beanFactory.instantinateAll();
		
		Area moon = new MoonArea();
		
		Plant moonAppleTree = new Plant((int) (168 * 1.5), "Moon Apple Tree", moon);
		Sprout moonAppleSprout = moonAppleTree.new Sprout();
		moonAppleSprout.addPlantProduct(new ItemMoonApple());
		moonAppleTree.addSprout(moonAppleSprout);
		
		Plant moonPearTree = new Plant(168 * 2, "Moon Pear Tree", moon);
		Sprout moonPearSprout = moonAppleTree.new Sprout();
		moonPearSprout.addPlantProduct(new ItemMoonPear());
		moonPearTree.addSprout(moonPearSprout);
		
		Fence fence = new Fence(moon);
		
		Plant moonRaspberryPlant = new Plant(100, "Moon Raspberry Plant", moon);
		Sprout moonRaspberrySprout = moonRaspberryPlant.new Sprout();
		
		for (int i = 0; i < 100; ++i)
			moonRaspberrySprout.addPlantProduct(new ItemMoonRaspberry());
		
		moonRaspberryPlant.addSprout(moonRaspberrySprout);
		
		Person personNeznaika = new Person("Neznaika", 168, new MoonAtmosphereArea(), new Hat(Color.BLUE), new Shirt(Color.RED), 
				new Tie(Color.GREEN), new Trousers(Color.YELLOW), new Spacesuit(true)
				{
					private int breathTime = Integer.MAX_VALUE;
					
					public int getBreathTimes()
					{
						return breathTime;
					}
					
					public void decrementBreathTimesCounter()
					{
						if (wearing)
							--breathTime;
					}
				}, (Boots) beanFactory.getBean("boots"));
		
		personNeznaika.takeItem(new ItemParachute());
		
		long time0 = System.currentTimeMillis();
		
		double h = 10;
		
		double fallingTime = Math.sqrt(2 * h / (moon.getGravityAcceleration() - personNeznaika.getParachute().getDecelerationFactor()));
		
		System.out.printf("%s is falling down\n", personNeznaika.getName());
		
		while ((System.currentTimeMillis() - time0) < fallingTime * 1000)
		{
			try
			{
				personNeznaika.breath();
			} catch (BreathException e) {
				personNeznaika.die();
			}
			catch (PersonDeadException e) 
			{
				System.out.println(personNeznaika.getName() + " IS DEAD");
				return;
			}
		}
		
		System.out.printf("%s moonlit\n", personNeznaika.getName());
		
		personNeznaika.setArea(moon);
		personNeznaika.takeOffGasmask();
		
		moonAppleTree.wobble();
		moonPearTree.wobble();
		
		personNeznaika.goTo(moonAppleTree);
		personNeznaika.eat(moonAppleTree.getPlantProducts().get(0));
		
		ItemMoonPear moonPear = new ItemMoonPear(moonPearTree.getSprouts().get(0));
		personNeznaika.eat(moonPear);
		
		personNeznaika.addItems(moonPear);
		
		personNeznaika.dropItem(moonPear);
		
		personNeznaika.searchForFood();
		
		personNeznaika.goTo(fence);
		personNeznaika.eat(new ItemMoonRaspberry(moonRaspberryPlant.getSprouts().get(0)));
		
		Person personFix = new Person("Fix", 168, moon, new Cap(Color.RED), new Jacket(Color.RED), new Sandals(Color.RED));
		
		personFix.lookAt(personNeznaika);
		personFix.takeItem(new Item("Broom"));
		personFix.setActiveItem(new Item("Broom"));
		
		personNeznaika.eat(new ItemMoonRaspberry(moonRaspberryPlant.getSprouts().get(0)));
		
		fence.getSpringtrap().springTrap(personNeznaika);
		personFix.hit(personNeznaika);
		
		personFix.goTo(fence.getSpringtrap());
		personFix.breakThing(fence.getSpringtrap());
		
		Plant moonTomatoSapling = new Plant(3, "Moon Tomato Sapling", moon);
		Sprout moonTomatoSprout = moonTomatoSapling.new Sprout();
		moonTomatoSprout.addPlantProduct(new ItemMoonTomato());
		moonTomatoSapling.addSprout(moonTomatoSprout);
		
		Plant moonCucumberSapling = new Plant(4, "Moon Cucumber Sapling", moon);
		Sprout moonCucumberSprout = moonCucumberSapling.new Sprout();
		moonCucumberSprout.addPlantProduct(new ItemMoonCucumber());
		moonCucumberSapling.addSprout(moonCucumberSprout);
		
		personNeznaika.wander();
		personFix.wander();
		
		personNeznaika.lookAt(moonTomatoSapling);
		personNeznaika.lookAt(moonCucumberSapling);
		
		System.out.println(personNeznaika);
		System.out.println(personFix);
	}
}
