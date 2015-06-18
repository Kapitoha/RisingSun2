package com.springapp.mvc.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author kapitoha
 *
 */
public class CollectionTag {
    public static boolean contains(Collection<?> coll, Object o)
    {
	return coll.contains(o);
    }

    public static boolean containsByField(Collection<?> collection, String fieldName,
	    Object target)
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
	    catch (NoSuchFieldException | SecurityException | IllegalArgumentException
		    | IllegalAccessException e)
	    {
		System.err.println(e.getLocalizedMessage());
		continue;
	    }
	}
	return false;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValues(
	    Map<K, V> map, final boolean reverse)
    {
	Map<K, V> result = new LinkedHashMap<K, V>();
	Comparator<Map.Entry<K, V>> sortComparator = new Comparator<Map.Entry<K, V>>()
	{

	    @Override
	    public int compare(Entry<K, V> o1, Entry<K, V> o2)
	    {
		int res = 0;
		if (!reverse)
		    res = o1.getValue().compareTo(o2.getValue());
		else
		    res = o2.getValue().compareTo(o1.getValue());
		return res != 0 ? res : 1;
	    }
	};
	Set<Map.Entry<K, V>> sortedSet = new TreeSet<Map.Entry<K, V>>(sortComparator);
	sortedSet.addAll(map.entrySet());
	for (Entry<K, V> entry : sortedSet)
	{
	    result.put(entry.getKey(), entry.getValue());
	}
	return result;
    }
}
