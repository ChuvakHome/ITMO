package ru.itmo.lab6.collection;

import java.io.Serializable;
import java.time.LocalDateTime;

import ru.itmo.lab6.util.Autoinput;
import ru.itmo.lab6.util.Nullable;
import ru.itmo.lab6.util.Validation;
import ru.itmo.lab6.util.Validators;

public class Product implements Serializable, Comparable<Product>
{
	private static final long serialVersionUID = 3289398489228526797L;
	
	@Autoinput
	@Validation(Validators.PositiveIntegerValidator.class)
    private Integer id; //Поле не может быть null, 
	//Значение поля должно быть больше 0, 
	//Значение этого поля должно быть уникальным, 
	//Значение этого поля должно генерироваться автоматически
    
	@Validation(Validators.NotEmptyStringValidator.class)
	private String name; //Поле не может быть null, Строка не может быть пустой
	
    private Coordinates coordinates; //Поле не может быть null
    
    @Autoinput
    private java.time.LocalDateTime creationDate; //Поле не может быть null, 
    //Значение этого поля должно генерироваться автоматически
    
    @Validation(Validators.PositiveIntegerValidator.class)
    private int price; //Значение поля должно быть больше 0
    
    @Nullable
    private String partNumber; //Поле может быть null
    
    private float manufactureCost; 
    
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    
    private Person owner; //Поле не может быть null
    
	public int compareTo(Product o) 
	{
		return id == o.id ? 0 : Double.compare(manufactureCost, o.manufactureCost);
	}
	
	public Product()
	{
		this(0);
	}
	
	public Product(Integer id)
	{
		this.id = id;
		
		this.creationDate = LocalDateTime.now();
	}
	
	/**
	 * Sets id of product
	 * @param id of product
	 */
	public void setId(Integer id)
	{	
		if (this.id == 0)
		{
			if (id > 0)
				this.id = id;
		}
	}
	
	/**
	 * Returns id of this product
	 * @return id of the product
	 */
	public Integer getId()
	{
		return this.id;
	}
	
	/**
	 * Sets creationDate of this product
	 * @param creationDate
	 */
	public void setCreationDate(LocalDateTime creationDate)
	{	
		if (creationDate != null)
			this.creationDate = creationDate;
	}
	
	public LocalDateTime getCreationDate()
	{
		return this.creationDate;
	}
	
	public void setName(String name)
	{
		if (name != null && name.length() > 0)
			this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setCoordinates(Coordinates coordinates)
	{
		if (coordinates != null)	
			this.coordinates = coordinates;
	}
	
	public Coordinates getCoordinates()
	{
		return this.coordinates;
	}
	
	public void setPartNumber(String partNumber)
	{
		this.partNumber = partNumber;
	}
	
	public String getPartNumber()
	{
		return this.partNumber;
	}
	
	public void setPrice(int price)
	{
		if (price > 0)
			this.price = price;
	}
	
	public int getPrice()
	{
		return this.price;
	}
	
	public void setManufactureCost(float manufactureCost)
	{
		this.manufactureCost = manufactureCost;
	}
	
	public float getManufactureCost()
	{
		return this.manufactureCost;
	}
	
	public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure)
	{
		if (unitOfMeasure != null)
			this.unitOfMeasure = unitOfMeasure;
	}
	
	public UnitOfMeasure getUnitOfMeasure()
	{
		return this.unitOfMeasure;
	}
	
	public void setOwner(Person owner)
	{
		if (owner != null)	
			this.owner = owner;
	}
	
	public Person getOwner()
	{
		return this.owner;
	}
	
	public String toString()
	{
		String s = "[";
		
		String[] fields = ProductHandler.readFieldsFromProduct(this, true);
		
		for (int i = 0; i < fields.length - 1; ++i)
			s += fields[i] + ", ";
		
		s += fields[fields.length - 1] + "]";
		
		return s;
	}
}
