/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
public class CartProduct extends BaseModel{

	@Column(name = "quantity", columnDefinition = "int not null")
	@Min(1)
	private int quantity;
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "product_id", nullable = false)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Product product;

	
	public CartProduct() {
		super();
	}

	public CartProduct(@Min(1) int quantity, Product product) {
		super();
		this.quantity = quantity;
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
	
}
