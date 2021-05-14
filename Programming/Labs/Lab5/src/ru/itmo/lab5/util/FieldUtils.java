package ru.itmo.lab5.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import ru.itmo.lab5.collection.Coordinates;
import ru.itmo.lab5.collection.Location;
import ru.itmo.lab5.collection.Person;
import ru.itmo.lab5.collection.Product;
import ru.itmo.lab5.collection.UnitOfMeasure;

public class FieldUtils 
{
	private static Map<String, Pair> gettersAndSetters = new HashMap<String, Pair>();
	
	static
	{
		putGettersAndSetters();
	}
	
	private static void putGettersAndSetters()
	{
		gettersAndSetters.put("Product.id", new Pair(
		(target, value) -> 
		{
			if (target instanceof Product && value instanceof Integer)
				Product.class.cast(target).setId(Integer.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Product)
				return Product.class.cast(target).getId();
			else
				return null;
		}));
		
		gettersAndSetters.put("Product.name", new Pair(
		(target, value) -> 
		{
			if (target instanceof Product && value instanceof String)
				Product.class.cast(target).setName(String.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Product)
				return Product.class.cast(target).getName();
			else
				return null;
		}));
		
		gettersAndSetters.put("Product.coordinates", new Pair(
		(target, value) -> 
		{
			if (target instanceof Product && value instanceof Coordinates)
				Product.class.cast(target).setCoordinates(Coordinates.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Product)
				return Product.class.cast(target).getCoordinates();
			else
				return null;
		}));
		
		gettersAndSetters.put("Product.creationDate", new Pair(
		(target, value) ->
		{
			if (target instanceof Product && value instanceof LocalDateTime)
				Product.class.cast(target).setCreationDate(LocalDateTime.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Product)
				return Product.class.cast(target).getCreationDate();
			else
				return null;
		}));
		
		gettersAndSetters.put("Product.price", new Pair(
		(target, value) ->
		{
			if (target instanceof Product && value instanceof Integer)
				Product.class.cast(target).setPrice(Integer.class.cast(value).intValue());
		},
		
		target -> 
		{
			if (target instanceof Product)
				return Product.class.cast(target).getPrice();
			else
				return null;
		}));
		
		gettersAndSetters.put("Product.partNumber", new Pair(
		(target, value) ->
		{
			if (target instanceof Product && value instanceof String)
				Product.class.cast(target).setPartNumber(String.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Product)
				return Product.class.cast(target).getPartNumber();
			else
				return null;
		}));
		
		gettersAndSetters.put("Product.manufactureCost", new Pair(
		(target, value) ->
		{
			if (target instanceof Product && value instanceof Number)
				Product.class.cast(target).setManufactureCost(Number.class.cast(value).floatValue());
		},
		
		target -> 
		{
			if (target instanceof Product)
				return Product.class.cast(target).getManufactureCost();
			else
				return null;
		}));
		
		gettersAndSetters.put("Product.unitOfMeasure", new Pair(
		(target, value) ->
		{
			if (target instanceof Product && value instanceof UnitOfMeasure)
				Product.class.cast(target).setUnitOfMeasure(UnitOfMeasure.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Product)
				return Product.class.cast(target).getUnitOfMeasure();
			else
				return null;
		}));

		gettersAndSetters.put("Product.owner", new Pair(
		(target, value) ->
		{
			if (target instanceof Product && value instanceof Person)
				Product.class.cast(target).setOwner(Person.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Product)
				return Product.class.cast(target).getOwner();
			else
				return null;
		}));
		
		gettersAndSetters.put("Coordinates.x", new Pair( 
		(target, value) ->
		{
			if (target instanceof Coordinates && value instanceof Number)
				Coordinates.class.cast(target).setX(Number.class.cast(value).doubleValue());
		},
		
		target -> 
		{
			if (target instanceof Coordinates)
				return Coordinates.class.cast(target).getX();
			else
				return null;
		}));
		
		gettersAndSetters.put("Coordinates.y", new Pair( 
		(target, value) ->
		{
			if (target instanceof Coordinates && value instanceof Number)
				Coordinates.class.cast(target).setY(Number.class.cast(value).longValue());
		},
		
		target -> 
		{
			if (target instanceof Coordinates)
				return Coordinates.class.cast(target).getY();
			else
				return null;
		}));
		
		gettersAndSetters.put("Location.x", new Pair( 
		(target, value) ->
		{
			if (target instanceof Location && value instanceof Number)
				Location.class.cast(target).setX(Number.class.cast(value).doubleValue());
		},
		
		target -> 
		{
			if (target instanceof Location)
				return Location.class.cast(target).getX();
			else
				return null;
		}));
		
		gettersAndSetters.put("Location.y", new Pair( 
		(target, value) ->
		{
			if (target instanceof Location && value instanceof Integer)
				Location.class.cast(target).setY(Integer.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Location)
				return Location.class.cast(target).getY();
			else
				return null;
		}));
		
		gettersAndSetters.put("Location.name", new Pair( 
		(target, value) -> 
		{
			if (target instanceof Location && value instanceof String)
				Location.class.cast(target).setName(String.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Location)
				return Location.class.cast(target).getName();
			else
				return null;
		}));
		
		gettersAndSetters.put("Person.name", new Pair( 
		(target, value) -> 
		{
			if (target instanceof Person && value instanceof String)
				Person.class.cast(target).setName(String.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Person)
				return Person.class.cast(target).getName();
			else
				return null;
		}));
		
		gettersAndSetters.put("Person.passportID", new Pair( 
		(target, value) -> 
		{
			if (target instanceof Person && value instanceof String)
				Person.class.cast(target).setPassportID(String.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Person)
				return Person.class.cast(target).getPassportID();
			else
				return null;
		}));
		
		gettersAndSetters.put("Person.location", new Pair(
		(target, value) -> 
		{
			if (target instanceof Person && value instanceof Location)
				Person.class.cast(target).setLocation(Location.class.cast(value));
		},
		
		target -> 
		{
			if (target instanceof Person)
				return Person.class.cast(target).getLocation();
			else
				return null;
		}));
	}
	
	public static boolean contains(Field field)
	{
		return gettersAndSetters.containsKey(Converter.getFieldName(field));
	}
	
	public static void set(Field field, Object target, Object value)
	{
		if (gettersAndSetters.containsKey(Converter.getFieldName(field)))
			gettersAndSetters.get(Converter.getFieldName(field)).setter.set(target, value);
	}
	
	public static Object get(Field field, Object target)
	{
		if (gettersAndSetters.containsKey(Converter.getFieldName(field)))
			return gettersAndSetters.get(Converter.getFieldName(field)).getter.get(target);
		else
			return null;
	}
	
	private static class Pair
	{
		Setter setter;
		Getter getter;
		
		private Pair(Setter setter, Getter getter)
		{
			this.setter = setter;
			this.getter = getter;
		}
	}
}
