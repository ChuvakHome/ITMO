package ru.itmo.lab5.util;

public class Validators 
{
	public static class EmptyValidator implements Validator
	{
		public boolean validate(Object value) 
		{
			return true;
		}
	}
	
	public static class PositiveIntegerValidator implements Validator
	{
		public boolean validate(Object value) 
		{
			if (value instanceof Integer)
				return Integer.class.cast(value).intValue() > 0;
			else
				return false;
		}
	}
	
	public static class NotEmptyStringValidator implements Validator
	{
		public boolean validate(Object value) 
		{
			if (value instanceof String)
				return value.toString().length() > 0;
			else
				return false;
		}
	}
}
