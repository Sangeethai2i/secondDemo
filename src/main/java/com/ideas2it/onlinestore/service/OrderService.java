/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service;

import java.util.List;

import com.ideas2it.onlinestore.dto.OrderDTO;

/**
 * 
 * @author Aabid
 *
 */
public interface OrderService {
	
	/**
	 * This method takes a userId and an addressId as input,
	 * fetches and validates both of them and implements the
	 * cart checkout functionality by taking the current cart
	 * products and adding them to order.
	 * 
	 * @param userId(Id of the user)
	 * @param addressId(Id of default address of the user)
	 * @return OrderDTO(DTO object containing order details)
	 */
	public OrderDTO placeOrder(long addressId);
	
	/**
	 * This method takes an orderId as input and fetches the details
	 * of that particular order.
	 * 
	 * @param orderId(ID of a particular order)
	 * @return OrderDTO(DTO object with details of the order)
	 */
	public OrderDTO getOrderById(long orderId);
	
	/**
	 * This method takes a userId as input and retrieves all
	 * orders placed by that user.
	 * @param userId(Id of the user)
	 * @return List(return a list containing all the orders
	 * that have been places by that user)
	 */
	public List<OrderDTO> getAllOrders();
	
	/**
	 * This method takes an orderId as input and tries to cancel the 
	 * order if it is eligible for cancellation.
	 * 
	 * @param orderId(Id of the order that is to be cancelled)
	 * @return true if order is cancelled else returns false.
	 */
	public boolean updateOrder(long orderId);
	

}
