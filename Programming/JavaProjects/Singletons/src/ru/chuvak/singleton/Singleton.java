package ru.test.singleton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Singleton 
{	
	@SingletonInstance
	public static final Singleton INSTANCE = new Singleton();
	
	public Singleton()
	{	
		String className = "";
		
		try
		{
			throw new Exception();
		} 
		catch (Exception e) 
		{
			StackTraceElement[] stackTraceElements = e.getStackTrace();
			
			boolean check = false;
			
			for (int i = 0; i < stackTraceElements.length; ++i)
			{
				className = stackTraceElements[i].getClassName();
				
				if (check)
				{
					if (className.equals(getClass().getName()))
						check = false;
					else
					{
						try
						{
							if (!className.equals(getClass().getName()))
								throw new InstantiationError("Unable to create new instance for class " + getClass().getName() + " in class " + className);
						} catch (Exception ex) {}
					}
				}
				else if (className.equals(getClass().getName()) && stackTraceElements[i].getMethodName().equals("<init>"))
					check = true;
			}
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	public static @interface SingletonClass {}
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface SingletonInstance {}
}
