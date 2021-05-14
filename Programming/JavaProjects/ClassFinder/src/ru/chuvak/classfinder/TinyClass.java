package ru.chuvak.classfinder;

public abstract class TinyClass
{
	public abstract String getPackage();
	
	public abstract String getSimpleName();
	
	public abstract String getName();
	
	public abstract ClassLoader getClassLoader();
	
	public final Class<?> toClass()
	{
		try 
		{
			return getClassLoader().loadClass(getName());
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final String toString()
	{
		return getName();
	}
	
	public static TinyClass newTinyClass(String pkg, String simpleName, ClassLoader classLoader)
	{
		return new TinyClass() 
		{
			public String getPackage() 
			{
				return pkg;
			}

			public String getSimpleName() 
			{
				return simpleName;
			}

			public String getName() 
			{
				return pkg + "." + simpleName;
			}

			public ClassLoader getClassLoader() 
			{
				return classLoader != null ? classLoader : ClassLoader.getSystemClassLoader();
			}
		};
	}
}
