package com.ideas2it.onlinestore.util.customException;

import org.springframework.http.HttpStatus;

/**
 * This class is used to convert all message to custom exception
 *
 * @version 1.0
 * @author arunkumar			
 */
public class ProductExistException extends RuntimeException {

	public HttpStatus httpStatus;
	
	public ProductExistException(String message) {
		super(message);
	}
	
	public ProductExistException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
}
