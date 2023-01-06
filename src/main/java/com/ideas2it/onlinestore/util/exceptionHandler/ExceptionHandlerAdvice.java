/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.util.exceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ideas2it.onlinestore.model.Message;
import com.ideas2it.onlinestore.util.customException.CategoryExistException;
import com.ideas2it.onlinestore.util.customException.DataNotFoundException;
import com.ideas2it.onlinestore.util.customException.InsufficientStockException;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;
import com.ideas2it.onlinestore.util.customException.ProductExistException;
import com.ideas2it.onlinestore.util.customException.ResourcePersistenceException;

/**
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

	private Logger logger = LogManager.getLogger(ExceptionHandlerAdvice.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleInvalidArgumentException(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(OnlineStoreException.class)
	public Message handleOnlineStoreException(OnlineStoreException exception) {
		return getMessage(exception, exception.httpStatus);
	}
	
	@ExceptionHandler(CategoryExistException.class)
	public Message handleCategoryExistException(CategoryExistException exception) {
		return getMessage(exception, exception.httpStatus);
	}
	
	@ExceptionHandler(ResourcePersistenceException.class)
	public Message handleResourcePersistenceException(ResourcePersistenceException exception) {
		return getMessage(exception, exception.httpStatus);
	}
	
	@ExceptionHandler(ProductExistException.class)
	public Message handleProductExistException(ProductExistException exception) {
		return getMessage(exception, exception.httpStatus);
	}

	@ExceptionHandler(InsufficientStockException.class)
	public Message handleInsufficientStockException(InsufficientStockException exception) {
		return getMessage(exception, exception.httpStatus);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public Message handleUserNotFoundException(DataNotFoundException exception) {
		return getMessage(exception, exception.httpStatus);
	}

	public Message getMessage(RuntimeException ex, HttpStatus httpStatus) {
		logger.error(ex.getMessage());
		logger.error(ex.getStackTrace());
		return new Message(httpStatus.value(), new Date(), ex.getMessage());
	}

}
