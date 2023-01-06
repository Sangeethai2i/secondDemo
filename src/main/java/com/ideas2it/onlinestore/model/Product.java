/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption; 

/**
 * It is a simple JavaBean domain object representing a product.
 *
 * @author Sangeetha Ilangovan
 * @version 1.0
 * @since 09.12.2022
 */
@Entity
@Table(name = "product")
public class Product extends BaseModel {
	
	@Transient
	private int quantity;

	@Column(name = "price")
	private double price;
	
	@Transient
	private Date dateOfExpire;
	
	@Transient
	private Date dateOfManufacture;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sub_category_id")
    @LazyCollection(LazyCollectionOption.FALSE)
	private Category subCategory;
	
	@ManyToMany(mappedBy = "products")
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Wishlist> wishlists;
	
	public Product() {}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDateOfExpire() {
		return dateOfExpire;
	}

	public void setDateOfExpire(Date dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
	}

	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}

	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Category subCategory) {
		this.subCategory = subCategory;
	}

	public List<Wishlist> getWishlists() {
		return wishlists;
	}

	public void setWishlists(List<Wishlist> wishlists) {
		this.wishlists = wishlists;
	} 
}
