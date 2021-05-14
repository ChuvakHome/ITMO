package ru.itmo.lab5.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Stream;

import ru.itmo.lab5.collection.Coordinates;
import ru.itmo.lab5.collection.Location;
import ru.itmo.lab5.collection.Person;
import ru.itmo.lab5.collection.Product;
import ru.itmo.lab5.logging.LogHandler;
import ru.itmo.lab5.util.Autoinput;
import ru.itmo.lab5.util.Constants;
import ru.itmo.lab5.util.Converter;
import ru.itmo.lab5.util.FieldUtils;
import ru.itmo.lab5.util.Nullable;
import ru.itmo.lab5.util.Printer;
import ru.itmo.lab5.util.Utils;
import ru.itmo.lab5.util.Validation;
import ru.itmo.lab5.util.Validator;
import ru.itmo.lab5.util.Validators;

public abstract class ElementCommand extends AbstractCommand
{
	private static Field[] collectionClassFields;
	private static Field[] allFields;
	private static Map<String, Field> fieldByName = new HashMap<String, Field>();
	
	protected String[] elementFields;
	protected Product product;
	
	static
	{
		List<Field> tempList = new ArrayList<Field>();
		
		addClassFieldToList(tempList, Product.class);
		allFields = tempList.toArray(new Field[0]);
		tempList.removeIf(field -> field.isAnnotationPresent(Autoinput.class) || isComplex(field));
		
		collectionClassFields = tempList.toArray(new Field[0]);
	}
	
	private static void addClassFieldToList(List<Field> fields, Class<?> clazz)
	{
		for (Field field: clazz.getDeclaredFields())
		{
			if (!field.getType().equals(clazz))
			{
				fields.add(field);
				
				if (isComplex(field))
					addClassFieldToList(fields, field.getType());
				else
					fieldByName.put(Converter.getFieldName(field), field);
			}
		}
	}
	
	public static Field getFieldByName(String name)
	{
		return fieldByName.get(name);
	}
	
	private static boolean isComplex(Field f)
	{
		Class<?> clazz = f.getType();
		
		return !(clazz.isPrimitive() || 
				clazz.isEnum() ||
				clazz.equals(Boolean.class) ||
				clazz.equals(Byte.class) ||
				clazz.equals(Short.class) ||
				clazz.equals(Integer.class) ||
				clazz.equals(Long.class) ||
				clazz.equals(Float.class) ||
				clazz.equals(Double.class) ||
				clazz.equals(Character.class) ||
				clazz.equals(String.class) ||
				clazz.equals(LocalDateTime.class));
	}
	
	/**
	 * Reads new {@code Product} element. User enters all standard-types field in different lines
	 * @param reader {@code BufferedReader} object, which reads the lines 
	 * @param invite flag which indicates that method prints an invitation message (like "Enter your name: ") before the user enters the data
	 * @return {@code true} if all fields were read without any problems, {@code false} otherwise
	 */
	public boolean readElement(BufferedReader reader, boolean invite)
	{
		int k = 0;
		
		String message;
		String errMessage;
		String next = "";
		
		Product product = new Product();
		Person person = new Person();
		Coordinates coordinates = new Coordinates();
		Location location = new Location();
		
		for (k = 0; k < allFields.length; ++k)
		{	
			Field field = allFields[k];
			
			if (field.isAnnotationPresent(Autoinput.class))
				continue;
			else if (isComplex(field))
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
						while (k + 1 < allFields.length && !allFields[k + 1].getDeclaringClass().equals(field.getDeclaringClass()))
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
				
				return false;
			}
		}
		
		person.setLocation(location);
		product.setCoordinates(coordinates);
		product.setOwner(person);
		
		this.product = product;
		
		return true;
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
	
	public static Field[] getCollectionClassFields()
	{
		return collectionClassFields.clone();
	}
	
	public static Field[] getAllFields()
	{
		return Stream.of(allFields).filter(field -> !isComplex(field)).toArray(Field[]::new);
	}
}
