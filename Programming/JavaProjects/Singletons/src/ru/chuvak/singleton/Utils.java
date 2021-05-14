package ru.test.singleton;

public class Utils 
{
	static
	{
		System.out.println("LOL");
	}
	
	public static String getCallerClassName()
	{
		try
		{
			throw new Exception();
		} 
		catch (Exception e) 
		{
			if (e.getStackTrace().length < 3)
				return null;
			else
				return e.getStackTrace()[2].getClassName();
		}
	}
	
	public static Class<?> getCallerClass()
	{
		try
		{
			throw new Exception();
		} 
		catch (Exception e) 
		{
			if (e.getStackTrace().length < 3)
				return null;
			else
			{
				try
				{
					return Class.forName(e.getStackTrace()[2].getClassName());
				} catch (Exception ex) {}
			}
		}
		
		return null;
	}

	public static void singletonProtection()
	{
		String victimClass = "";
		String victimMethod = "";
		String callerClass = "";
		
		try
		{
			throw new Exception();
		}
		catch (Exception e) 
		{
			StackTraceElement[] trace = e.getStackTrace();
			
			if (trace.length < 3)
				return;
			
			victimClass = trace[1].getClassName();
			victimMethod = trace[1].getMethodName();
			callerClass = trace[2].getClassName();
		}
		
		if (victimMethod.equals("<init>") && !victimClass.equals(callerClass))
			throw new InstantiationError("Unable to create new instance for class " + victimClass + " in class " + callerClass);
	}
}
