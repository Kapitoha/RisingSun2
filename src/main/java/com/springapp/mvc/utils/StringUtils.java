package com.springapp.mvc.utils;

import org.springframework.web.util.HtmlUtils;

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
	return null == string || match.find();
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
		if (rez.matches("(=\".*" + keyword + ".*\")")) continue;
		else
		matcher.appendReplacement(sb,
			"<span class=\"highlight\">" + rez + "</span>");
	    }
	    matcher.appendTail(sb);
	}
	else return content;
	return sb.toString();
    }

    public static String clearFromJavaScriptInjection(String string)
    {
	StringBuffer sb;
	if (null != string && !checkIsEmpty(string))
	{
	    Pattern pattern = Pattern.compile("(<\\s*script.*>.*<\\s*/\\s*script\\s*>)", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(string);
	    sb = new StringBuffer();
	    while (matcher.find())
	    {
		String rez = matcher.group();
		matcher.appendReplacement(sb, HtmlUtils.htmlEscape(rez));
	    }
	    matcher.appendTail(sb);
	}
	else
	{
	    return string;
	}
	return sb.toString();
    }

    public static String clearHTMLTags(String string)
    {
	return (null != string && !string.isEmpty())? HtmlUtils.htmlEscape(string, "utf8") : string;
    }
    
    public static int parseInt(String string, int defaultValue)
    {
	int rez = defaultValue;
	try
	{
	    rez = Integer.parseInt(string);
	}
	catch (NumberFormatException e)
	{
	}
	return rez;
    }

}
