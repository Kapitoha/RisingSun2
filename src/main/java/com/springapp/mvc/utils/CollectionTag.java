package com.springapp.mvc.utils;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author kapitoha
 *
 */
public class CollectionTag {
    public static boolean contains(Collection<?> coll, Object o)
    {
	return coll.contains(o);
    }
    
    public static boolean containsByField(Collection<?> collection, String fieldName, Object target)
    {
	for (Object object : collection)
	{
	    try
	    {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		if (field.get(object).equals(target))
		return true;
	    }
	    catch (NoSuchFieldException | SecurityException
		    | IllegalArgumentException | IllegalAccessException e)
	    {
		System.err.println(e.getLocalizedMessage());
		continue;
	    }
	}
	return false;
    }
}
