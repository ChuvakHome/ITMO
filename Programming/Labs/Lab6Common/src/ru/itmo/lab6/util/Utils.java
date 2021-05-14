package ru.itmo.lab6.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import ru.itmo.lab6.logging.LogHandler;

public class Utils 
{
	public static String readLine(BufferedReader bufferedReader)
	{
		try
		{
			return bufferedReader.readLine();
		} 
		catch (Exception e) 
		{
			LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
		}
		
		return null;
	}
	
	public static boolean isReady(BufferedReader bufferedReader)
	{
		try
		{
			return bufferedReader.ready();
		} 
		catch (Exception e) 
		{
			LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
		}
		
		return false;
	}
	
	public static void close(Closeable closeable)
	{
		try
		{
			closeable.close();
		} 
		catch (Exception e) 
		{
			LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
		}
	}
	
	public static boolean isReadable(File f)
	{
		return f != null && f.exists() && f.canRead();
	}
	
	public static boolean isWritable(File f)
	{
		return f != null && f.exists() && f.canWrite();
	}
	
	/**
	 * Stops the current thread for {@code timeout} time.
	 * <p></p>Notice: this method used only for synchronization between output and error streams
	 * @param timeout time of sleeping
	 */
	public static void sleep(long timeout)
	{
		try
		{
			Thread.sleep(timeout);
		} catch (InterruptedException e) {}
	}
	
	public static<T> T newInstance(Class<T> clazz)
	{
		try
		{
			return clazz.newInstance();
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	public static class Pair
	{
		public Class<?> type;
		public Object val;
		
		public Pair(Class<?> type, Object val)
		{
			this.type = type;
			this.val = val;
		}
	}
	
	public static class Pairs
	{
		private List<Pair> pairs;
		
		public Pairs()
		{
			pairs = new ArrayList<Pair>();
		}
		
		public void addPair(Pair p)
		{
			if (p != null)
				pairs.add(p);
		}
		
		public void addPair(Class<?> type, Object val)
		{
			if (type != null && val != null)
				pairs.add(new Pair(type, val));
		}
		
		public Class<?>[] getTypes()
		{
			List<Class<?>> types = new ArrayList<Class<?>>();
			
			pairs.forEach(pair -> types.add(pair.type));
			
			return types.toArray(new Class<?>[0]);
		}
		
		public Object[] getValues()
		{
			List<Object> types = new ArrayList<Object>();
			
			pairs.forEach(pair -> types.add(pair.val));
			
			return types.toArray(new Object[0]);
		}
	}
}
