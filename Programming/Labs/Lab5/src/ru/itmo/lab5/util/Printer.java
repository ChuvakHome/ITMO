package ru.itmo.lab5.util;

import java.io.PrintStream;

public class Printer 
{	
	public static final PrintStream OUT = System.out;
	public static final PrintStream ERR = System.err;
	
	public static void printfln(PrintStream printStream, String format, Object... args)
	{
		if (printStream != null)
		{
			printStream.printf(format, args);
			printStream.println();
			Utils.sleep(5);
		}
	}
}
