/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * The stock class have stock attributes
 * This class contain getter and setter method for Inventory attributes
 *
 * @version 1.0
 * @author arunkumar
 */
@Entity
@Getter
@Setter
public class Stock extends BaseModel {
 
	@Column(nullable = false)
	private String productName;
	@Column(nullable = false)	
	private int quantity;
	@Column(nullable = false)	
	private Date dateOfManufacture;
	private Date dateOfExpire;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private User seller;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Product product;
}
