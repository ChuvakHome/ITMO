package ru.itmo.lab7.output;

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

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.collection.ProductHandler;
import ru.itmo.lab6.logging.LogHandler;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;
import ru.itmo.lab6.util.Utils;

public class OutputHandler 
{
	public void saveCollectionToFile(Collection<Product> collection)
	{
		File f = null;
		
		if (collection == null || collection.isEmpty())
			return;

		if (System.getenv(Constants.ENV_VAR_NAME) != null)
			f = new File(System.getenv(Constants.ENV_VAR_NAME));
		
		if (Utils.isWritable(f))
		{
			try 
			{
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f));
	
				StringBuilder stringBuilder = new StringBuilder("");
				
				ProductHandler.STANDARD_FIELDS.forEach(field -> stringBuilder.append(String.format(";%s.%s", field.getDeclaringClass().getSimpleName(), field.getName())));
				
				bufferedWriter.write(stringBuilder.toString().substring(1));
				bufferedWriter.newLine();
				
				collection.stream().forEachOrdered(product ->
				{
					String[] values = ProductHandler.readFieldsFromProduct(product);
					
					stringBuilder.setLength(0);
					
					Stream.of(values).forEachOrdered(str -> stringBuilder.append(";").append(str));
					
					try 
					{
						bufferedWriter.append(stringBuilder.substring(1));
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
		else
			LogHandler.LOGGER.log(Level.SEVERE, Constants.COLLECTION_FILE_IS_NOT_WRITABLE_MSG);
	}
	
	public Deque<Product> readCollection(File file)
	{
		Deque<Product> deque = new ArrayDeque<Product>();
		
		if (!Utils.isReadable(file))
		{
			Printer.printfln(Printer.ERR, Constants.COLLECTION_FILE_DOES_NOT_EXISTS_MSG, file != null ? file.getAbsolutePath() : "");
			
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
				
				Stream.of(values).forEachOrdered(type -> fieldsList.add(ProductHandler.getFieldByName(type)));
				fields = fieldsList.toArray(new Field[0]);
				
				while (scn.hasNextLine())
				{
					values = read(scn);
					
					if (values == null || values.length != fields.length)
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
