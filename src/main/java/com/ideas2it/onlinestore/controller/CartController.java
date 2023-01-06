/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.onlinestore.dto.CartDTO;
import com.ideas2it.onlinestore.service.CartService;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.customAnnotations.CustomRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This a controller class for cart module
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */

@RestController
@RequestMapping("/sd")
public class CartController {

	
	private final CartService cartService;
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	/**
	 * This method adds a product to the cart of currently 
	 * logged in user. It takes the product id and desired 
	 * quantity that the customer wishes to add to his cart
	 * and passes the information to the corresponding 
	 * service where the product is added to the cart. The
	 * method return a cartDTO type object showing updated
	 * state of the cart.
	 * 
	 * @param productId (Unique identifier for the product)
	 * @param quantity (desired quantity of the product)
	 * @return CartDTO 
	 */
	@PostMapping("cart")
	@ApiOperation(value = Constant.ADD_PRODUCT,
			notes = Constant.ADD_PRODUCT_DESCRIPTION, 
			response = CartDTO.class)
	public ResponseEntity<CartDTO> addToCart(
			@ApiParam(name = "ID", value = "ID of the Product") 
			@RequestParam("ID") long productId,
			@ApiParam(name = "Quantity", value = "Desired Quantity of the product") 
			@RequestParam("Quantity") int quantity) {
		
		CartDTO savedCart = cartService.addToCart(productId, quantity);		
		return new ResponseEntity<>(savedCart, HttpStatus.CREATED);		
	}
	
	/**
	 * This method removes a product from the cart of currently 
	 * logged in user. It takes the product id corresponding 
	 * to the product that is to be removed from the cart and
	 * passes the information to the corresponding service 
	 * where the product is removed from the cart. This method
	 * returns a cart DTO type object showing updated status 
	 * of the cart.
	 * 
	 * @param productId (Unique identifier for the product)
	 * @return CartDTO
	 */
	@PatchMapping("cart")
	@ApiOperation(value = Constant.REMOVE_PRODUCT,
			notes = Constant.REMOVE_PRODUCT_DESCRIPTION, 
			response = CartDTO.class)
	public ResponseEntity<CartDTO> removeFromCart(
			@ApiParam(name = "ID", value = "ID of the Product") 
			@RequestParam("ID") long productId) {		
		CartDTO savedCart = cartService.removeFromCart(productId);		
		return new ResponseEntity<>(savedCart, HttpStatus.OK);		
	}
	
	/**
	 * This method displays the current status of the cart.
	 * It shows the list of products that are in the cart for 
	 * the currently logged-in user and the total value of the
	 * cart at that point of time.
	 * 
	 * @return CartDTO
	 */
	@GetMapping("cart")
	@ApiOperation(value = Constant.CART,
	notes = Constant.CART_DESCRIPTION, 
	response = CartDTO.class)
	public ResponseEntity<CartDTO> getCart() {		
		return new ResponseEntity<>(cartService.getCart(), HttpStatus.OK);
	}
	
}
