package ru.test.singleton;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ru.test.classloader.ClassFinder;
import ru.test.singleton.Singleton.SingletonInstance;

public class Singletons 
{
	public static final Map<String, Object> ALL;
	
	static
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		ClassFinder.ALL_CLASSES.forEach(clazz -> 
		{
			if (Singleton.class.isAssignableFrom(clazz) || clazz.isAnnotationPresent(Singleton.SingletonClass.class))
			{
				try 
				{
					for (Field field: clazz.getDeclaredFields())
					{
						if (field.getType().getName().equals(clazz.getName()) && field.isAnnotationPresent(SingletonInstance.class))
						{
							try
							{
								map.put(clazz.getName(), field.get(clazz));
							} 
							catch (IllegalAccessException e) {}
							
							return;
						}
					}
				} 
				catch (Exception e) {e.printStackTrace();}
			}
		});
		
		ALL = Collections.unmodifiableMap(map);
	}
	
	public static<T> T getSingleton(Class<?> singletonClass, Class<T> typeClass)
	{
		if (singletonClass != null && typeClass != null)
			return typeClass.cast(ALL.get(singletonClass.getName()));
		else
			return null;
	}
	
	public static<T> T getSingleton(Class<T> singletonClass)
	{
		return getSingleton(singletonClass, singletonClass);
	}
}
