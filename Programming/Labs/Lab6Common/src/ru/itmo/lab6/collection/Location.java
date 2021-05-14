package ru.itmo.lab6.collection;

import java.io.Serializable;

import ru.itmo.lab6.util.Nullable;
import ru.itmo.lab6.util.Validation;
import ru.itmo.lab6.util.Validators;

public class Location implements Serializable
{
	private static final long serialVersionUID = -4265022139291865116L;

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
