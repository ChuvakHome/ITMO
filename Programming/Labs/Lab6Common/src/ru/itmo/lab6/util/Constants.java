package ru.itmo.lab6.util;

public class Constants 
{
	/**
	 * The name of environment variable with path to collection file. This file contains collection in csv format   
	 */
	public static final String ENV_VAR_NAME = "DEQUE_FILE";
	
	public static final String ENVIRONMENT_VARIABLE_NOT_DEFIEND_MSG = "Environment variable %s isn't defined";
	public static final String COLLECTION_FILE_CANNOT_BE_READ_MSG = "Collection file %s cannot be read";
	public static final String COLLECTION_FILE_DOES_NOT_EXISTS_MSG = "Collection file %s doesn't exists";
	public static final String COLLECTION_FILE_INCORRECT_DATA_MSG = "Collection file %s contains incorrect data";
	public static final String COLLECTION_FILE_CONTAINS_SYNTAX_ERROR_MSG = "Collection file %s contains invalid syntax";
	
	public static final String COLLECTION_FILE_IS_NOT_WRITABLE_MSG = "Collection file is not writable";
	
	public static final String UNKNOWN_COMMAND_MSG = "Unknown command \'%s\'%s";
	public static final String WRONG_USAGE_MSG = "Wrong usage of command \'%s\'%s";
	
	public static final String INVITE_MSG = "Enter %s (%s)";
	public static final String NEED_ENTER_NULLABLE_MSG = "Do you want to enter %s (%s): ";
	
	public static final String INVALID_FIELD_VALUE_MSG = "Invalid value for %s field";
	public static final String FIELD_CANNOT_BE_NULL_MSG = "The field %s (%s) cannot be null";
	public static final String DUPLICATED_ID_MSG = "Duplicated Id in collection file %s";
	
	public static final String UNABLE_TO_READ_FILE = "Unable to read file %s";
	
	public static final String DEQUE_FULL_MSG = "Deque is full";
	public static final String SOMETHING_WENT_WRONG_MSG = "Something went wrong";
	
	public static final String STANDARD_ADDRESS = "127.0.0.1";
	public static final int STANDARD_PORT = 1357;
	
	public static final String SERVER_UNAVALIABLE = "Server (ipv4: %s, port: %d) unavaliable now";
	
	public static final class SerialVersionUID
	{
		public static final long COMMAND = -6522084499730446986L;
		public static final long COMMAND_ARGS = -3590881162589473185L;
		
		public static final long CLIENT_PACKET = 7544713829302033251L;
		public static final long SERVER_PACKET = -6139332772290914625L;;
	}
}
