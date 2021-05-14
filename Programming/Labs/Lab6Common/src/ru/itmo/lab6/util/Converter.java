package ru.itmo.lab6.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public class Converter 
{
	public static boolean isParsable(Class<?> type, String v)
	{
		if (type.isEnum())
			return parseEnumConstIgnoreCase(type, v) != null;
		else
		{
			try
			{
				parse0(type, v);
				return true;
			} 
			catch (ClassCastException | NumberFormatException e) 
			{
				return false;
			}
		}
	}
	
	public static Object parse(Class<?> type, String v)
	{
		return isParsable(type, v) ? parse0(type, v) : null;
	}
	
	private static Object parse0(Class<?> type, String s)
	{
		if (type.isPrimitive())
		{
			if (type.equals(Boolean.TYPE))
				return Boolean.valueOf(s);
			else if (type.equals(Byte.TYPE))
				return Byte.valueOf(s);
			else if (type.equals(Short.TYPE))
				return Short.valueOf(s);
			else if (type.equals(Character.TYPE))
				return Character.valueOf(s.charAt(0));
			else if (type.equals(Integer.TYPE))
				return Integer.valueOf(s);
			else if (type.equals(Long.TYPE))
				return Long.valueOf(s);
			else if (type.equals(Float.TYPE))
				return Float.valueOf(s);
			else if (type.equals(Double.TYPE))
				return Double.valueOf(s);
			else
				return null;
		}
		else if (type.equals(Boolean.class))
			return Boolean.valueOf(s);
		else if (type.equals(Byte.class))
			return Byte.valueOf(s);
		else if (type.equals(Short.class))
			return Short.valueOf(s);
		else if (type.equals(Character.class))
			return Character.valueOf(s.charAt(0));
		else if (type.equals(Integer.class))
			return Integer.valueOf(s);
		else if (type.equals(Long.class))
			return Long.valueOf(s);
		else if (type.equals(Float.class))
			return Float.valueOf(s);
		else if (type.equals(Double.class))
			return Double.valueOf(s);
		else if (s == null || s.isEmpty())
			return null;
		else if (type.equals(String.class))
			return s;
		else if (type.equals(LocalDateTime.class))
			return LocalDateTime.parse(s);
		else
			return null;
	}
	
	public static boolean parseBoolean(String s)
	{
		return Boolean.parseBoolean(s);
	}
	
	public static byte parseByte(String s)
	{
		return Byte.parseByte(s);
	}
	
	public static short parseShort(String s)
	{
		return Short.parseShort(s);
	}
	
	public static char parseChar(String s)
	{
		return s.charAt(0);
	}
	
	public static int parseInt(String s)
	{
		return Integer.parseInt(s);
	}
	
	public static long parseLong(String s)
	{
		return Long.parseLong(s);
	}
	
	public static float parseFloat(String s)
	{
		return Float.parseFloat(s);
	}
	
	public static double parseDouble(String s)
	{
		return Double.parseDouble(s);
	}
	
	public static LocalDateTime parseLocalDateTime(String s)
	{
		return LocalDateTime.parse(s);
	}
	
	public static<T extends Enum<T>> T parseEnumConst(Class<T> enumClass, String s)
	{
		return Enum.valueOf(enumClass, s);
	}
	
	public static Object parseEnumConstIgnoreCase(Class<?> enumClass, String s)
	{
		if (enumClass.isEnum())
		{
			try
			{
				Method m = enumClass.getMethod("name");
				Object[] values = enumClass.getEnumConstants();
				
				for (Object e: values)
				{
					if ((m.invoke(e).toString().equalsIgnoreCase(s)))
						return e;
				}
			} 
			catch (Exception e) 
			{
				return null;
			}
		}
			
		return null;
	}
	
	public static boolean checkFor(String[] values, Class<?>... types)
	{
		if (values.length != types.length)
			return false;
		
		for (int i = 0; i< values.length; ++i)
		{
			if (!isParsable(types[i], values[i]))
				return false;
		}
		
		return true;
	}
	
	public static Object getObj(Class<?> type, Object... objects)
	{
		Optional<Object> optional =  Arrays.stream(objects).filter(obj -> type.isInstance(obj)).findAny();
		
		return optional.isPresent() ? optional.get() : null;
	}
	
	public static String getFieldName(Field field)
	{
		return field != null ? field.getDeclaringClass().getSimpleName() + "." + field.getName() : null;
	}
}
