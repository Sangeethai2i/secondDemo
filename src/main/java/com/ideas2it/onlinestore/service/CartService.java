/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service;

import com.ideas2it.onlinestore.dto.CartDTO;

/**
 * @author Aabid
 *
 */
public interface CartService {
	
	/**
	 * This method takes a Cart Data transfer Object as an input 
	 * and creates a cart for a particular user. This method is 
	 * automatically called whenever a user registers and a cart
	 * is allocated to that particular user.
	 * 
	 * @param cartDTO(object containing details of user)
	 * @return CartDTO(object containing details of cart allocated
	 * to a user)
	 */
	CartDTO createCart(CartDTO cartDTO);
	
	/**
	 * This methods takes an InputDTO type object as a parameter.
	 * This parameter contains user details, product details and 
	 * the quantity of that product that the user wishes to add
	 * to his cart.
	 * 
	 * @param cartProductInputDto(contains userId, productId, and 
	 * product quantity that is to be added to cart
	 * @return (CartDTO showing products added to the cart)
	 *
	 */
	public CartDTO addToCart(long productId, int quantity);
	
	/**
	 * This methods takes an InputDTO type object as a parameter.
	 * This parameter contains user details, product details that is
	 * to be removed from the cart.
	 * 
	 * @param cartProductInputDto(contains userId, productId of the 
	 * product that is to be removed from the cart)
	 * @return (CartDTO showing updated status of the cart)
	 */
	public CartDTO removeFromCart(long productId);
	
	/**
	 * This methods takes userId as an input and returns the
	 * associated cart with the user. 
	 * @param userId(userId of a particular user)
	 * @return (CartDTO showing details of the cart)
	 */
	public CartDTO getCart();
	
	

}
