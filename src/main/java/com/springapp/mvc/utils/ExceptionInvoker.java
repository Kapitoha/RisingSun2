package com.springapp.mvc.utils;

/**
 * @author kapitoha
 *
 */
public class ExceptionInvoker {
    public static class PageNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    }
    public static class AccessDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    }
}
