package ru.itmo.lab5.util;

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
}
