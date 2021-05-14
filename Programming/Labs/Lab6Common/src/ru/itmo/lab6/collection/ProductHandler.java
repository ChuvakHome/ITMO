package ru.itmo.lab6.collection;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import ru.itmo.lab6.logging.LogHandler;
import ru.itmo.lab6.util.Autoinput;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Converter;
import ru.itmo.lab6.util.FieldUtils;
import ru.itmo.lab6.util.Nullable;
import ru.itmo.lab6.util.Printer;
import ru.itmo.lab6.util.Utils;
import ru.itmo.lab6.util.Validation;
import ru.itmo.lab6.util.Validator;
import ru.itmo.lab6.util.Validators;

public class ProductHandler
{
	public static final List<Field> STANDARD_FIELDS;
	public static final List<Field> COLLECTION_CLASS_FIELDS;
	public static final List<Field> ALL_FIELDS;
	private static Map<String, Field> fieldByName = new HashMap<String, Field>();
	
	static
	{
		List<Field> tempList = new ArrayList<Field>();
		
		addClassFieldToList(tempList, Product.class);
		ALL_FIELDS = Collections.unmodifiableList(new ArrayList<Field>(tempList));
		
		tempList.removeIf(FieldUtils::isComplex);
		
		STANDARD_FIELDS = Collections.unmodifiableList(new ArrayList<Field>(tempList));
		
		tempList.removeIf(field -> field.isAnnotationPresent(Autoinput.class));
		
		COLLECTION_CLASS_FIELDS = Collections.unmodifiableList(tempList);
	}
	
	private static void addClassFieldToList(List<Field> fields, Class<?> clazz)
	{
		for (Field field: clazz.getDeclaredFields())
		{
			if (!Modifier.isStatic(field.getModifiers()))
			{
				fields.add(field);
					
				if (FieldUtils.isComplex(field))
				{
					if (!field.getType().equals(clazz))
						addClassFieldToList(fields, field.getType());
				}
				else
					fieldByName.put(Converter.getFieldName(field), field);
			}
		}
	}
	
	public static Field getFieldByName(String name)
	{
		return fieldByName.get(name);
	}
	
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
		
		int i = 0;
		
		String[] values = new String[STANDARD_FIELDS.size()];
		
		try
		{
			for (i = 0; i < STANDARD_FIELDS.size(); ++i)
			{
				values[i] = "";
				
				Object obj = Converter.getObj(STANDARD_FIELDS.get(i).getDeclaringClass(), product, owner, coordinates, location);
				
				if (FieldUtils.contains(STANDARD_FIELDS.get(i)))
					values[i] = (names ? Converter.getFieldName(STANDARD_FIELDS.get(i)) + "=" : "") + String.valueOf(FieldUtils.get(STANDARD_FIELDS.get(i), obj));
			}
		} 
		catch (Exception e) 
		{
			LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
			
			return null;
		}
		
		return values;
	}
	
	public static Product readElement(BufferedReader reader, boolean invite)
	{
		int k = 0;
		
		String message;
		String errMessage;
		String next = "";
		
		Product product = new Product();
		Person person = new Person();
		Coordinates coordinates = new Coordinates();
		Location location = new Location();
		
		for (k = 0; k < ALL_FIELDS.size(); ++k)
		{	
			Field field = ALL_FIELDS.get(k);
			
			if (field.isAnnotationPresent(Autoinput.class))
				continue;
			else if (FieldUtils.isComplex(field))
			{
				if (field.isAnnotationPresent(Nullable.class))
				{
					String ans = "";
					message = String.format(Constants.NEED_ENTER_NULLABLE_MSG, field.getDeclaringClass().getSimpleName(), field.getName());
					
					while (!(ans.equals("n") || ans.equals("no") ||
						ans.equals("y") || ans.equals("yes")))
						
					ans = next(reader, message, "", invite).toLowerCase();
					
					if (ans.equals("n") || ans.equals("no"))
					{
						while (k + 1 < ALL_FIELDS.size() && !ALL_FIELDS.get(k + 1).getDeclaringClass().equals(field.getDeclaringClass()))
							++k;
					}
				}
				
				continue;
			}
			
			message = String.format(Constants.INVITE_MSG, field.getDeclaringClass().getSimpleName(), field.getName());
			
			Class<?> fieldType = field.getType();
			
			if (fieldType.isEnum())
			{	
				message += " (";
				
				Object[] a = fieldType.getEnumConstants();
				
				for (int i = 0; i < a.length - 1; ++i)
					message += a[i] + ", ";
				
				message += a[a.length - 1] + ")";
			}
				
			message += ": ";
			
			if (invite)
				Printer.OUT.print(message);
			
			try 
			{
				next = reader.readLine().trim();
			} 
			catch (IOException e) 
			{
				e.printStackTrace(Printer.ERR);
			}
			
			if (!field.isAnnotationPresent(Nullable.class) && next.isEmpty())
			{
				errMessage = String.format(Constants.FIELD_CANNOT_BE_NULL_MSG + "\n", field.getDeclaringClass().getSimpleName(), field.getName());
				Printer.ERR.printf(errMessage);
				Utils.sleep(5);
				next = next(reader, message, errMessage, invite);
			}
			
			try 
			{
				boolean flag = true;
				
				Validator validator = new Validators.EmptyValidator();
				
				while (flag)
				{
					if (field.isAnnotationPresent(Validation.class))
						validator = field.getAnnotation(Validation.class).value().newInstance();
					
					Object value = fieldType.isEnum() ? Converter.parseEnumConstIgnoreCase(fieldType, next) : Converter.parse(fieldType, next);
					
					if (Converter.isParsable(fieldType, next) && validator.validate(value))
					{
						FieldUtils.set(field, Converter.getObj(field.getDeclaringClass(), product, person, coordinates, location), fieldType.isEnum() ? Converter.parseEnumConstIgnoreCase(fieldType, next) : Converter.parse(fieldType, next));
						flag = false;
					}
					else
					{
						
						errMessage = String.format(Constants.FIELD_CANNOT_BE_NULL_MSG + "\n", field.getDeclaringClass().getSimpleName(), field.getName());
						Printer.printfln(Printer.ERR, Constants.INVALID_FIELD_VALUE_MSG, Converter.getFieldName(field));
						
						next = next(reader, message, errMessage, invite);
					}
				}
			} catch (Exception e) 
			{
				LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
				
				return null;
			}
		}
		
		person.setLocation(location);
		product.setCoordinates(coordinates);
		product.setOwner(person);
		
		return product;
	}
	
	private static String next(BufferedReader reader, String message, String errMessage, boolean invite)
	{
		String next = "";
		
		while (next.isEmpty())
		{
			if (invite)
				Printer.OUT.print(message);
			
			try 
			{
				next = reader.readLine().trim();
			} 
			catch (IOException e) 
			{
				LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
			}
			
			if (next.isEmpty())
			{
				Printer.ERR.print(errMessage);
				Utils.sleep(5);
			}
		}
		
		return next.split("\\s")[0];
	}
}
