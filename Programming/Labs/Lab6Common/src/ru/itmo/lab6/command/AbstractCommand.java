package ru.itmo.lab6.command;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.stream.Stream;

import ru.itmo.lab6.command.Command.Args;
import ru.itmo.lab6.util.FieldUtils;

public abstract class AbstractCommand<E extends Args> implements Command
{
	private static final long serialVersionUID = 1L;
	
	protected transient CommandHandler commandHandler;
	protected String commandName;
	
	@SuppressWarnings("unchecked")
	protected Class<E> argsClass = (Class<E>) Args.class;
	
	public AbstractCommand()
	{
		this("");
	}
	
	@SuppressWarnings("unchecked")
	public AbstractCommand(String name)
	{
		if (name != null && name.length() > 0)
			commandName = name;
		else
			commandName = getName0();
		
		Optional<Class<?>> optional = Stream.of(getClass().getClasses()).filter(clazz ->
		{
			int mod = clazz.getModifiers();
			
			return Modifier.isFinal(mod) && Modifier.isStatic(mod) && Args.class.isAssignableFrom(clazz);
		}).findFirst();
		
		if (optional.isPresent())
			argsClass = (Class<E>) optional.get();
	}
	
	private String getName0()
	{
		String className = getClass().getSimpleName().replace("Command", "");
		
		if (className == null || className.isEmpty())
			return null;
		
		className = Character.toLowerCase(className.charAt(0)) + className.substring(1);
		
		for (char c = 'A'; c <= 'Z'; ++c)
			className = className.replace("" + c, "_" + Character.toLowerCase(c));
		
		return className;
	}
	
	public void execute(Object args) 
	{
		if (argsClass.isInstance(args))
			execute(argsClass.cast(args));
	}
	
	public void execute(E args) {}
	
	public int standardArgsCount()
	{
		int argsCount = 0;
		
		for (Field f: argsClass.getDeclaredFields())
		{
			if (!Modifier.isStatic(f.getModifiers()))
			{
				if (FieldUtils.isComplex(f))
					return argsCount;
				else
					++argsCount;
			}
		}
		
		return argsCount;
	}
	
	public Class<E> getArgsList()
	{
		return argsClass;
	}
	
	public void setCommandHandler(CommandHandler commandHandler)
	{
		this.commandHandler = commandHandler;
	}
	
	public String getName() 
	{
		return commandName;
	}
	
	public CommandType getCommandType()
	{
		return CommandType.SERVER_COMMAND;
	}
}
