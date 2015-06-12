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
    
    public static boolean checkIsEmpty(String string)
    {
	final Pattern pattern = Pattern.compile("^\\s*$");
	Matcher match = pattern.matcher(string);
	return string == null || match.find();
    }
    
    public static String highlightKeywords(String keyword, String content)
    {
	StringBuffer sb;
	if (keyword != null && !checkIsEmpty(keyword) && null != content)
	{
	    sb = new StringBuffer();
	    Pattern pattern = Pattern.compile("(=\".*"+keyword+".*\")|"+keyword, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(content);
	    while (matcher.find())
	    {
		String rez = matcher.group();
		if (rez.matches("(=\".*"+keyword+".*\")")) continue;
		else
		matcher.appendReplacement(sb,
			"<span class=\"highlight\">" + rez + "</span>");
	    }
	    matcher.appendTail(sb);
	}
	else return content;
	return sb.toString();
    }

}
