/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */
@Entity
@SQLDelete(sql = "Update cart SET deleted = '1' where cart_id=?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = '0'")
public class Cart extends BaseModel {
	
	@Transient
	private double cartTotal;
	
	@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name = "cart_id", nullable = false)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CartProduct> cartProducts;
	
	@OneToOne()
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	public double getCartTotal() {
		return cartTotal;
	}

	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}

	public List<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
