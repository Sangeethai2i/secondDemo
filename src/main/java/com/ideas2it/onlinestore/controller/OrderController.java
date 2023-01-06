/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.onlinestore.dto.OrderDTO;
import com.ideas2it.onlinestore.model.Message;
import com.ideas2it.onlinestore.service.OrderService;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.customAnnotations.CustomRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * This is a controller class for Order module
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */
@RestController
@RequestMapping("/sd1")
public class OrderController {

	
	private final OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	/**
	 * This methods implements cart check-out logic. It 
	 * tracks the contents of cart for the currently logged-in
	 * user and takes an addressId as a parameter that
	 * corresponds to the default delivery address of the user.
	 * The method return an OrderDTO object that contains order details.
	 * 
	 * @param addressId(Id of the default address of the user)
	 * @return OrderDTO(contains details of the placed order) 
	 */
	@PostMapping("order")
	@ApiOperation(value = Constant.PLACE_ORDER,
	notes = Constant.PLACE_ORDER_DESCRIPTION, 
	response = OrderDTO.class)
	public ResponseEntity<OrderDTO> placeOrder(
			@ApiParam(name = "ID", value = "Delivery address ID") 
			@RequestParam("ID") long addressId) {
		
		OrderDTO savedOder = orderService.placeOrder(addressId);		
		return new ResponseEntity<OrderDTO>(savedOder, HttpStatus.CREATED);
	}
	
	/**
	 * This method is user to retrieve the information for a
	 * particular order using the orderId corresponding to that
	 * order. 
	 * 
	 * @param id(Id of the order whose details are to be fetched)
	 * @return OrderDTO(contains required details of the order)
	 */
	@GetMapping("order")
	@ApiOperation(value = Constant.SHOW_PARTICULAR_ORDER,
	notes = Constant.SHOW_PARTICULAR_ORDER_DESCRIPTION, 
	response = OrderDTO.class)
	public ResponseEntity<OrderDTO> getOrderById(
			@ApiParam(name = "ID", value = "Order ID") 
			@RequestParam("ID") long orderId) {
		
		return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
	}
	
	/**
	 * This method is used to retrieve all the orders that have
	 * been placed so far by a particular user.
	 * 
	 * @return ResponseEntity( returns a response which either contains 
	 * a list of all the orders placed by the user or a custom message
	 * incase user has not placed any orders so far.
	 */
	@GetMapping("order/all")
	@ApiOperation(value = Constant.SHOW_ALL_ORDERS,
	notes = Constant.SHOW_ALL_ORDERS_DESCRIPTION, 
	response = OrderDTO.class)
	public ResponseEntity<List<OrderDTO>> getAllOrders() {

		return new ResponseEntity<List<OrderDTO>>(orderService.getAllOrders(), HttpStatus.OK);
		
	}
	
	/**
	 * This method takes an orderId as input and calls on the 
	 * corresponding service to to cancel the order. A response 
	 * is generated which contains the status of order cancellation.
	 * 
	 * @param orderId(Id of order that the user wishes to cancel)
	 * @return ResponseEntity (contains custom message that displays
	 * the status of order cancellations)
	 */
	@PatchMapping("order")	
	@ApiOperation(value = Constant.UPDATE_ORDER,
			notes = Constant.UPDATE_ORDER_DESCRIPTION, 
			response = Message.class)
	public ResponseEntity<Message> updateOrder(
			@ApiParam(name = "ID", value = "Order ID") 
			@RequestParam("ID") long orderId) {
		
		if (orderService.updateOrder(orderId)) {
			Message message = new Message(
					HttpStatus.ACCEPTED.value(),
					new Date(),
					Constant.ORDER_CANCELLED
					);
			return new ResponseEntity<Message>(message, HttpStatus.NO_CONTENT);
		}
			Message message = new Message(
				HttpStatus.NOT_MODIFIED.value(),
				new Date(),
				Constant.ORDER_CANCELATION_FAILURE
				);
		return new ResponseEntity<Message>(message, HttpStatus.NOT_MODIFIED);
	}
	
}
