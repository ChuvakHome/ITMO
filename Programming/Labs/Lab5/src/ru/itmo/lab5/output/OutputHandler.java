package ru.itmo.lab5.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.stream.Stream;

import ru.itmo.lab5.collection.Product;
import ru.itmo.lab5.collection.ProductHandler;
import ru.itmo.lab5.command.ElementCommand;
import ru.itmo.lab5.logging.LogHandler;
import ru.itmo.lab5.util.Constants;
import ru.itmo.lab5.util.Printer;
import ru.itmo.lab5.util.Utils;

public class OutputHandler 
{
	public void saveCollectionToFile(Collection<Product> collection)
	{
		File f = new File(System.getenv(Constants.ENV_VAR_NAME));
		
		try 
		{
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f));
			
			Field[] fields = ElementCommand.getAllFields();
			
			String title = "";
			
			for (Field field: fields)
				title += String.format(";%s.%s", field.getDeclaringClass().getSimpleName(), field.getName());
			
			bufferedWriter.write(title.substring(1));
			bufferedWriter.newLine();
			
			collection.stream().forEachOrdered(product ->
			{
				String[] values = ProductHandler.readFieldsFromProduct(product);
				
				String line = "";
				
				for (String v: values)
					line += ";" + v;
				
				line = line.substring(1);
				
				try 
				{
					bufferedWriter.append(line);
					bufferedWriter.newLine();
				} 
				catch (Exception e) 
				{
					e.printStackTrace(Printer.ERR);
					Utils.sleep(5);
				}
			});
			
			bufferedWriter.close();
		} 
		catch (Exception e) 
		{
			LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
		}
	}
	
	public Deque<Product> readCollection(File file)
	{
		Deque<Product> deque = new ArrayDeque<Product>();
		
		if (file == null || !file.exists())
		{
			Printer.printfln(Printer.ERR, Constants.COLLECTION_FILE_DOES_NOT_EXISTS_MSG, file.getAbsolutePath());
			
			return deque;
		}
		
		try
		{
			Scanner scn = new Scanner(file);
			
			Field[] fields;
			
			if (scn.hasNextLine())
			{
				String[] values = read(scn);
				List<Field> fieldsList = new ArrayList<Field>();
				
				if (values == null)
				{
					Printer.printfln(Printer.ERR, Constants.COLLECTION_FILE_CONTAINS_SYNTAX_ERROR_MSG, file.getPath());
					
					return deque;
				}
				
				Stream.of(values).forEachOrdered(type -> fieldsList.add(ElementCommand.getFieldByName(type)));
				fields = fieldsList.toArray(new Field[0]);
				
				while (scn.hasNextLine())
				{
					values = read(scn);
					
					if (values == null)
					{
						Printer.printfln(Printer.ERR, Constants.COLLECTION_FILE_INCORRECT_DATA_MSG, file.getAbsolutePath());
						
						deque.clear();
						
						return deque;
					}

					Product temp = ProductHandler.createProduct(fields, values);
					
					if (temp != null)
						deque.add(temp);
				}
			}
			
			return deque;
		} 
		catch (Exception e) 
		{
			LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
		}
		
		return deque;
	}
	
	private String[] read(Scanner scn)
	{
		if (scn.hasNextLine())
		{
			String line = scn.nextLine();
			
			return line.startsWith(";") || line.endsWith(";") ? null : line.split(";"); 
		}
		else
			return null;
	}
}
