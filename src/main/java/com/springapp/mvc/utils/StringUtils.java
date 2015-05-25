package com.springapp.mvc.utils;

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
}
