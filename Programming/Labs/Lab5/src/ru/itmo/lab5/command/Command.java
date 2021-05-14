package ru.itmo.lab5.command;

public interface Command 
{	
	/**
	 * Validates the arguments
	 * @param args array of {@code String} which command gets from {@code CommandHandler}
	 * @return {@code true} if arguments are valid, {@code false} otherwise
	 */
	boolean check(String... args);
	
	/**
	 * Runs execution of the command
	 * @param args array of <tt>{@code String}</tt> which command gets from {@code CommandHandler} 
	 */
	void execute(String... args);

	/**
	 * Gets the name of the command. The name is what user need to write in console to use a certain command 
	 * @return the name of the command
	 */
	String getName();
	
	/**
	 * Gets info about the command. This method is used in <i>info</i> command
	 * @return information about the command
	 */
	String getInfo();
	
	/**
	 * Gets how many standard types (primitive types, object types of primitive types, {@code String}, {@code LocalDateTime}) arguments need command to be executed excluding {element}.
	 * <p>For example command <i>info</i> returns 0</p> 
	 * <p><i>add</i> also returns 0 because it works only with {element}</p>
	 * <p><i>update</i> returns 1 because it needs id argument and {element}</p>
	 * @return number of arguments which has standard type
	 */
	int argsCount();
}
