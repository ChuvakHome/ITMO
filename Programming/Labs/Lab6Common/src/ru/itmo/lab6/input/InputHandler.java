package ru.itmo.lab6.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.logging.Level;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.collection.ProductHandler;
import ru.itmo.lab6.command.Command;
import ru.itmo.lab6.command.CommandHandler;
import ru.itmo.lab6.logging.LogHandler;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Converter;
import ru.itmo.lab6.util.Printer;
import ru.itmo.lab6.util.Utils;
import ru.itmo.lab6.util.Utils.Pairs;
import ru.itmo.lab6.util.Validation;
import ru.itmo.lab6.util.Validator;
import ru.itmo.lab6.util.Validators;

public abstract class InputHandler
{
	protected CommandHandler commandHandler;
	
	public InputHandler(CommandHandler commandHandler)
	{
		this.commandHandler = commandHandler;
	}
	
	public abstract boolean needInvitation();
	
	public void beforeInput() {}
	
	public boolean ready(InputStream inputStream, BufferedReader bufferedReader)
	{
		try 
		{
			if (inputStream == null)
				return false;
			else if (bufferedReader == null)
				return false;
			
			return inputStream.available() >= 0 && bufferedReader.ready();
		} 
		catch (IOException e) 
		{
			return false;
		}
	}
	
	public void input(InputStream inputStream)
	{
		String command = "";
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		boolean flag;
		
		while (ready(inputStream, bufferedReader) && !command.equals(CommandHandler.EXIT_COMMAND_NAME))
		{
			beforeInput();
			
			flag = true;
			
			String line = Utils.readLine(bufferedReader);
			
			if (line == null)
				break;
			else if (line.isEmpty())
				continue;
			
			line = line.trim();
			
			String[] args = line.split("\\s");
			
			command = args[0];
			
			Command cmd = commandHandler.getCommandByName(command);
			
			args = Arrays.copyOfRange(args, 1, args.length);
			
			String s = " in script \'%s\'";
			
			s = commandHandler.isScriptExecuting() ? String.format(s, commandHandler.getCurrentScript()) : "";
			
			if (cmd == null)
			{	
				Printer.printfln(Printer.ERR, Constants.UNKNOWN_COMMAND_MSG, command, s);
				continue;
			}
			else if (args.length != cmd.standardArgsCount())
			{
				Printer.printfln(Printer.ERR, Constants.WRONG_USAGE_MSG, cmd.getName(), s);
				continue;
			}
			
			Class<? extends Command.Args> clazz = cmd.getArgsList();
			
			int i = 0;
			
			Pairs pairs = new Pairs();
			
			for (Field field: clazz.getDeclaredFields())
			{
				if (Modifier.isStatic(field.getModifiers()))
					continue;
				
				Validator validator = new Validators.EmptyValidator();
				
				if (field.isAnnotationPresent(Validation.class))
					validator = Utils.newInstance(field.getAnnotation(Validation.class).value());
				
				Object val;
				
				if (field.getType() == Product.class)
				{
					try
					{
						val = ProductHandler.readElement(bufferedReader, needInvitation());
						pairs.addPair(field.getType(), val);
						
						if (val == null)
						{
							Printer.printfln(Printer.ERR, Constants.WRONG_USAGE_MSG, cmd.getName(), s);
							flag = false;
							break;
						}
					} 
					catch (Exception e) 
					{
						LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
					}
				}
				else if (Converter.isParsable(field.getType(), args[i]))
				{
					val = Converter.parse(field.getType(), args[i++]);
					
					try
					{
						if (validator.validate(val))
							pairs.addPair(field.getType(), val);
						else
							Printer.printfln(Printer.ERR, Constants.WRONG_USAGE_MSG, cmd.getName(), s);
					} 
					catch (Exception e) 
					{
						LogHandler.LOGGER.log(Level.SEVERE, Constants.SOMETHING_WENT_WRONG_MSG, e);
					}
				}
				else
					Printer.printfln(Printer.ERR, Constants.WRONG_USAGE_MSG, cmd.getName(), s);
			}
			
			if (flag)
			{
				
				try	
				{
					commandHandler.executeCommandByName(command, clazz.getConstructor(pairs.getTypes()).newInstance(pairs.getValues()));
				} catch (Exception e) {e.printStackTrace();}
			}
		}
		
		Utils.close(bufferedReader);
	}
}