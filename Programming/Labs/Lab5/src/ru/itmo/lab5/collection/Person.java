package ru.itmo.lab5.collection;

import ru.itmo.lab5.util.Nullable;
import ru.itmo.lab5.util.Validation;
import ru.itmo.lab5.util.Validators;

public class Person 
{
	@Validation(Validators.NotEmptyStringValidator.class)
    private String name; //Поле не может быть null, Строка не может быть пустой
    
    @Nullable
    private String passportID; //Поле может быть null
    
    @Nullable
    private Location location; //Поле может быть null
    
    public void setName(String name)
    {
    	if (name != null && name.length() > 0)
    		this.name = name;
    }
    
    public String getName()
    {
    	return this.name;
    }
    
    public void setPassportID(String passportID)
    {
    	this.passportID = passportID;
    }
    
    public String getPassportID()
    {
    	return this.passportID;
    }
    
    public void setLocation(Location location)
    {
    	this.location = location;
    }
    
    public Location getLocation()
    {
    	return this.location;
    }
}
