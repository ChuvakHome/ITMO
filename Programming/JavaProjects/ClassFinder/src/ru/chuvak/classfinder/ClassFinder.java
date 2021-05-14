package ru.chuvak.classfinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class ClassFinder
{
	public static final List<TinyClass> ALL_CLASSES;
	
	static
	{
		List<TinyClass> files = scan();
		
		ALL_CLASSES = files != null ? Collections.unmodifiableList(files) : null;
	}
	
	private static List<TinyClass> scan()
	{
		List<TinyClass> classes = new ArrayList<TinyClass>();
		
		Stream.of(System.getProperty("java.class.path").split(File.pathSeparator)).forEach(sourcePath ->
		{
			File file = new File(sourcePath);
			
			if (file != null && file.exists() && file.canRead())
			{
				ClassLoader classLoader = ClassLoader.getSystemClassLoader();
				
				if (file.isDirectory())
					classes.addAll(scan(file, classLoader));
				else
				{
					try 
					{
						classes.addAll(scan(new JarFile(file), classLoader));
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		return classes;
	}
	
	public static List<TinyClass> scan(JarFile jarFile, ClassLoader classLoader)
	{
		if (jarFile == null || classLoader == null)
			return null;
		
		List<TinyClass> classes = new ArrayList<TinyClass>();
		
		jarFile.stream().forEachOrdered(entry -> 
		{			
			try
			{
				if (entry.getName().endsWith(".class"))
				{
					String name = entry.getName();
					
					int i = name.lastIndexOf('.');
					
					name = name.substring(0, i).replace(File.separatorChar, '.');
					
					i = name.lastIndexOf('.');
					
					String pkg = name.substring(0, i);
					String simpleName = name.substring(i + 1);
					
					classes.add(TinyClass.newTinyClass(pkg, simpleName, classLoader));
				}
			} catch (Exception e) {e.printStackTrace();}
		});
		
		return classes;
	}
	
	public static List<TinyClass> scan(File dir, ClassLoader classLoader)
	{
		if (dir != null && dir.exists() && dir.canRead() && classLoader != null)
		{
			Path mainDir = dir.toPath();
			
			if (dir.isDirectory())
				return search(mainDir, dir.listFiles(), classLoader);
			else
				return new ArrayList<TinyClass>();
		}
		else
			return null;
	}
	
	private static List<TinyClass> search(Path mainDir, File[] files, ClassLoader classLoader)
	{
		if (mainDir == null || !Files.exists(mainDir) || Files.notExists(mainDir) || files == null || classLoader == null)
			return null;
		
		List<TinyClass> classList = new ArrayList<TinyClass>();
		
		Stream.of(files).forEachOrdered(file -> 
		{
			if (file != null && file.exists() && file.canRead())
			{
				if (file.isDirectory())
					classList.addAll(search(mainDir, file.listFiles(), classLoader));
				else if (file.isFile() && file.getName().endsWith(".class"))
				{
					try
					{
						String s = mainDir.relativize(file.toPath()).toString().replace(File.separatorChar, '.');
						String fileName = file.getName();
						
						classList.add(TinyClass.newTinyClass(s.substring(0, s.length() - fileName.length() - 1), fileName.replace(".class", ""), classLoader));
					} catch (Exception e) {}
				}
			}
		});
		
		return classList;
	}
}
