package ru.itmo.lab5.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.util.logging.Level;

import ru.itmo.lab5.logging.LogHandler;

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
}
