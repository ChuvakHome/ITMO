package ru.itmo.lab7.collection;

import java.io.File;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import ru.itmo.lab6.collection.Product;
import ru.itmo.lab6.collection.ProductHandler;
import ru.itmo.lab6.util.Constants;
import ru.itmo.lab6.util.Printer;
import ru.itmo.lab6.util.Utils;
import ru.itmo.lab7.output.OutputHandler;

public class CollectionHandler 
{
	private ArrayDeque<Product> collection = new ArrayDeque<Product>();
	private OutputHandler outputHandler;
	public Queue<Integer> freeIds = new ArrayDeque<Integer>();
	
	public CollectionHandler(OutputHandler outputHandler)
	{
		this.outputHandler = outputHandler;
	}
	
	/**
	 * Fills collection from file. The path of file contains in environment variable {@code DEQUE_FILE}
	 * @see ru.itmo.lab6.util.Constants#ENV_VAR_NAME
	 */
	public void fillCollection()
	{
		String path = System.getenv(Constants.ENV_VAR_NAME);
		
		if (path == null)
		{
			Printer.printfln(Printer.ERR, Constants.ENVIRONMENT_VARIABLE_NOT_DEFIEND_MSG, Constants.ENV_VAR_NAME);
			return;
		}
		
		File file = new File(path);
		
		if (!Utils.isReadable(file))
		{
			Printer.printfln(Printer.ERR, Constants.COLLECTION_FILE_CANNOT_BE_READ_MSG, path);
			return;
		}
		
		collection = new ArrayDeque<Product>(outputHandler.readCollection(file));
		Set<Integer> set = new HashSet<Integer>();
		
		for (Product product: collection)
		{
			if (set.contains(product.getId()))
			{
				Printer.printfln(Printer.ERR, Constants.DUPLICATED_ID_MSG, path);
				collection.clear();
				return;
			}
			
			set.add(product.getId());
		}
	}
	
	/**
	 * Adds product to the collection
	 * @param product element which will be add to the collection
	 */
	public void addProduct(Product product)
	{		
		if (collection.size() < Integer.MAX_VALUE)
		{
			if (freeIds.isEmpty())
			{
				int max = collection.isEmpty() ? 0 : collection.stream().max((p1, p2) -> p1.getId().compareTo(p2.getId())).get().getId();
				product.setId(max + 1);
			}
			else
				product.setId(freeIds.poll());
			
			collection.add(product);
		}
		else
			Printer.printfln(Printer.ERR, Constants.DEQUE_FULL_MSG);
	}
	
	public void removeIf(Predicate<Product> predicate)
	{
		Iterator<Product> iterator = collection.iterator();
		
		while (iterator.hasNext())
		{
			Product product = iterator.next(); 
					
			if (predicate.test(product))
			{
				iterator.remove();
				freeIds.offer(product.getId());
			}
		}
	}
	
	/**
	 * Saves collection to file. The path of file contains in environment variable {@code DEQUE_FILE} 
	 */
	public void saveCollection()
	{
		outputHandler.saveCollectionToFile(collection);
	}
	
	/**
	 * Prints all elements of collection and also prints total size of collection
	 */
	public void printCollection()
	{
		Printer.printfln(Printer.OUT, "TOTAL SIZE: %d", collection.size());
		
		if (collection.isEmpty())
			return;
			
		ProductHandler.STANDARD_FIELDS.forEach(field -> Printer.OUT.printf("/ %s.%s ", field.getDeclaringClass().getSimpleName(), field.getName()));
		
		Printer.OUT.println("/");
		
		collection.stream().forEach(product ->
		{
			Stream.of(ProductHandler.readFieldsFromProduct(product)).forEach(value -> Printer.OUT.printf("/ %s ", value));
			Printer.OUT.println("/");
		});
	}
	
	/**
	 * Returns collection
	 * @return collection
	 */
	public ArrayDeque<Product> getCollection()
	{
		return collection;
	}
}
