/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.ideas2it.onlinestore.model.Category;
import com.ideas2it.onlinestore.util.constants.Constant;

/**
 * It is a simple JavaBean domain object representing a product.
 *
 * @author Sangeetha Ilangovan
 * @version 1.0
 * @since 20.12.2022
 */
public class ProductDTO {
	
	private long id;
	
	@Min(1)
	@JsonProperty(access = Access.WRITE_ONLY)
	private int quantity;

	@Min(1)
	private double price;
	
	@NotNull(message = Constant.EXPIRY_DATE_COMPULSORY)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Date dateOfExpiry;
	
	@NotNull(message = Constant.MANUFACTURE_DATE_COMPULSORY)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Date dateOfManufacture;
	
	@NotBlank(message = Constant.DESCRIPTION_COMPULSORY)
	private String description;
	
	@NotBlank(message = Constant.PRODUCT_NAME_COMPULSORY)
	@Pattern(regexp= Constant.REGEX_FOR_NAME, message = Constant.INVALID_NAME)
	private String name;
	
	@NotNull(message = Constant.BRAND_COMPULSORY)
	@JsonProperty(access = Access.WRITE_ONLY)
	private BrandDTO brand;
	
	@NotNull(message = Constant.CATEGORY_COMPULSORY)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Category category;
	
	@NotNull(message = Constant.SUB_CATEGORY_COMPULSORY)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Category subCategory;
	
	@JsonIgnore
	private List<WishlistDTO> wishlists;
	
	public ProductDTO() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	} 
	
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

	public Date getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(Date dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
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

	public BrandDTO getBrand() {
		return brand;
	}

	public void setBrand(BrandDTO brand) {
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

	public List<WishlistDTO> getWishlists() {
		return wishlists;
	}

	public void setWishlists(List<WishlistDTO> wishlists) {
		this.wishlists = wishlists;
	}

}