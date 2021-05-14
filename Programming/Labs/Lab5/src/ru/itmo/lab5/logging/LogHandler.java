package ru.itmo.lab5.logging;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogHandler 
{
	public static final Logger LOGGER = Logger.getLogger(LogHandler.class.getName());
	
	private static final String SIMPLE_FORMATTER_PROPERTY = "java.util.logging.SimpleFormatter.format";
	private static final String LOG_FORMAT = "[%1$tc][%2$s][%4$s]: %5$s:%6$s%n";
	
	public static void init(String filename)
	{
		init(new File(filename));
	}
	
	public static void init(File file)
	{
		try
		{
			LogManager.getLogManager().readConfiguration(new FileInputStream(file));
		}
		catch (Exception e) 
		{
			System.setProperty(SIMPLE_FORMATTER_PROPERTY, LOG_FORMAT);
		}
	}
}
