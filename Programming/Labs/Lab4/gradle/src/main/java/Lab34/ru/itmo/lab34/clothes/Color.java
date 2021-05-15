package ru.itmo.lab34.clothes;

import java.util.Objects;

public class Color implements IColor
{
	private int r;
	private int g;
	private int b;
	
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color BLUE = new Color(0, 0, 255);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color CYAN = new Color(0, 255, 255);
	public static final Color RED = new Color(255, 0, 0);
	public static final Color MAGENTA = new Color(255, 0, 255);
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color WHITE = new Color(255, 255, 255);
	
	public Color(int red, int green, int blue)
	{
		r = red;
		g = green;
		b = blue;
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof Color)
		{
			Color c = (Color) o;
			
			return c.r == r && c.g == g && c.b == b;
		}
		else
			return false;
	}
	
	public String toString()
	{
		if (equals(BLACK))
			return "black";
		else if (equals(BLUE))
			return "blue";
		else if (equals(GREEN))
			return "green";
		else if (equals(CYAN))
			return "cyan";
		else if (equals(RED))
			return "red";
		else if (equals(MAGENTA))
			return "magenta";
		else if (equals(YELLOW))
			return "yellow";
		else if (equals(WHITE))
			return "white";
		else
			return String.format("color with %.2f%s of red %.2f%s of green and %.2f%s of blue", r / 2.55, "%", g / 2.55, "%", b / 2.55, "%");
	}
	
	public int hashCode()
	{
		return Objects.hash(r, g, b);
	}
	
	public int getRed()
	{
		return r;
	}
	
	public int getGreen()
	{
		return g;
	}
	
	public int getBlue()
	{
		return b;
	}
}
