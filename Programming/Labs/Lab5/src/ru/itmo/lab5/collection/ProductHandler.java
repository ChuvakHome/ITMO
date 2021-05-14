package ru.itmo.lab5.collection;

import java.lang.reflect.Field;
import java.util.logging.Level;

import ru.itmo.lab5.command.ElementCommand;
import ru.itmo.lab5.logging.LogHandler;
import ru.itmo.lab5.util.Constants;
import ru.itmo.lab5.util.Converter;
import ru.itmo.lab5.util.FieldUtils;
import ru.itmo.lab5.util.Nullable;
import ru.itmo.lab5.util.Printer;
import ru.itmo.lab5.util.Validation;
import ru.itmo.lab5.util.Validator;
import ru.itmo.lab5.util.Validators;

public class ProductHandler
{
	public static Product createProduct(Field[] fields, String[] values)
	{	
		Product product = new Product();
		Person owner = new Person();
		Coordinates coordinates = new Coordinates();
		Location location = new Location();
		Object value;
		
		int i = 0;
		
		try
		{
			for (i = 0; i < fields.length; ++i)
			{
				if (fields[i] == null)
					continue;
				else if (values[i] == null)
					values[i] = "";
				
				Object obj = Converter.getObj(fields[i].getDeclaringClass(), product, owner, coordinates, location);				
				Validator validator = new Validators.EmptyValidator();
				
				if (values[i].isEmpty())
					value = null;
				else
				{
					if (fields[i].getType().isEnum())
						value = Converter.parseEnumConstIgnoreCase(fields[i].getType(), values[i]);
					else
						value = Converter.parse(fields[i].getType(), values[i]);
				}
				
				if (fields[i].isAnnotationPresent(Validation.class))
					validator = fields[i].getAnnotation(Validation.class).value().newInstance();
				
				if (Converter.isParsable(fields[i].getType(), values[i]) && validator.validate(value))
					FieldUtils.set(fields[i], obj, value);
				else if (!fields[i].isAnnotationPresent(Nullable.class) && values[i].isEmpty())
				{
					Printer.printfln(Printer.ERR, Constants.FIELD_CANNOT_BE_NULL_MSG, fields[i].getDeclaringClass(), fields[i].getName());
					return null;
				}
			}
		} 
		catch (Exception e) 
		{
			LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
			
			return null;
		}
		
		owner.setLocation(location);
		product.setCoordinates(coordinates);
		product.setOwner(owner);
		
		return product;
	}
	
	public static String[] readFieldsFromProduct(Product product)
	{	
		return readFieldsFromProduct(product, false);
	}
	
	public static String[] readFieldsFromProduct(Product product, boolean names)
	{	
		Person owner = product.getOwner();
		Coordinates coordinates = product.getCoordinates();
		Location location = owner != null ? owner.getLocation() : null;
		
		Field[] fields = ElementCommand.getAllFields();
		
		int i = 0;
		
		String[] values = new String[fields.length];
		
		try
		{
			for (i = 0; i < fields.length; ++i)
			{
				values[i] = "";
				
				Object obj = Converter.getObj(fields[i].getDeclaringClass(), product, owner, coordinates, location);
				
				if (FieldUtils.contains(fields[i]))
					values[i] = (names ? Converter.getFieldName(fields[i]) + "=" : "") + String.valueOf(FieldUtils.get(fields[i], obj));
			}
		} 
		catch (Exception e) 
		{
			LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
			
			return null;
		}
		
		return values;
	}
	
	public static Product clone(Product source)
	{
		return createProduct(ElementCommand.getAllFields(), readFieldsFromProduct(source));
	}
}
