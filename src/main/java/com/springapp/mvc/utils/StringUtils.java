package com.springapp.mvc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author kapitoha
 *
 */
public class StringUtils {
    public static final String FIELD_NAME_PATTERN = "A-Za-z0-9";
    public static boolean checkIfAllowed(String string)
    {
	final Pattern pattern = Pattern.compile("^["+FIELD_NAME_PATTERN+"]+$");
	Matcher match = pattern.matcher(string);
	return match.find();
    }
    /**
     * It's a miracle converting of "abra-kadabra" into real path.
     * @param string
     * @return
     */
    public static String decodeString(String string)
    {
	String u = null;
	try
	{
	    u = URLDecoder.decode(string, "UTF-8");
	}
	catch (UnsupportedEncodingException e)
	{
	    System.err.println("Cannot decode url, so return default");
	    return string;
	}
	return u;
    }
}
