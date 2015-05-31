package com.springapp.mvc.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author kapitoha
 *
 */
public class Exceptions {
    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public static class AccessDeniedException extends RuntimeException {
        
    }

}
