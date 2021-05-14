package ru.itmo.lab6.collection;

import java.io.Serializable;

public class Coordinates implements Serializable
{
	private static final long serialVersionUID = 2109283354661464699L;
	
	private double x;
    private long y;
    
    public void setX(double x)
    {
    	this.x = x;
    }
    
    public double getX()
    {
    	return this.x;
    }
    
    public void setY(long y)
    {
    	this.y = y;
    }
    
    public long getY()
    {
    	return this.y;
    }
}
