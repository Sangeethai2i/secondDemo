/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
/**
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */
public class CartDTO {
	
	private long id;
	
	private double cartTotal;	
	
	private List<CartProductDTO> cartProducts;	
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private UserDTO user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}

	public List<CartProductDTO> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<CartProductDTO> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	
}

