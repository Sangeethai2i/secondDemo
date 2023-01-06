package com.ideas2it.onlinestore.util.customException;

import org.springframework.http.HttpStatus;

/**
 * This class is used to convert all message to custom exception
 *
 * @version 1.0
 * @author arunkumar			
 */
public class DataNotFoundException extends RuntimeException {

	public HttpStatus httpStatus;
	
	public DataNotFoundException(String message) {
		super(message);
	}
	
	public DataNotFoundException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
}
