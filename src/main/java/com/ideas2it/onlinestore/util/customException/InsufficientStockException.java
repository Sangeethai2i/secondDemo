package com.ideas2it.onlinestore.util.customException;

import org.springframework.http.HttpStatus;

public class InsufficientStockException extends RuntimeException{

	public HttpStatus httpStatus;
	
	public InsufficientStockException(String message) {
		super(message);
	}
	
	public InsufficientStockException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
}
