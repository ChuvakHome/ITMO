package ru.itmo.lab5.collection;

import ru.itmo.lab5.util.Nullable;
import ru.itmo.lab5.util.Validation;
import ru.itmo.lab5.util.Validators;

public class Location 
{
    private double x;
    
    @Validation(Validators.PositiveIntegerValidator.class)
    private Integer y; //Поле не может быть null
    
    @Nullable
    private String name; //Поле может быть null
    
    public void setX(double x)
    {
    	this.x = x;
    }
    
    public double getX()
    {
    	return this.x;
    }
    
    public void setY(Integer y)
    {
    	if (y != null)
    		this.y = y;
    }
    
    public Integer getY()
    {
    	return this.y;
    }
    
    public void setName(String name)
    {
    	this.name = name;
    }
    
    public String getName()
    {
    	return this.name;
    }
}
