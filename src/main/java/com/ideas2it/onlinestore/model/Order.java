/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.ideas2it.onlinestore.util.constants.OrderStatus;
/**
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */
@Entity
@SQLDelete(sql = "Update `order` SET deleted = '1' where order_id=?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = '0'")
@Table(name = "`order`")
public class Order extends BaseModel{
	
	@Column(columnDefinition = "datetime default current_timestamp")
	private LocalDate date;
	
	@Column(columnDefinition = "double not null")
	private double amount;
	
	@Enumerated(EnumType.STRING)
    private OrderStatus status; 
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", nullable = false)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<OrderProduct> orderProducts;	

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Address address;

	
	public Order() {
		super();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}	
	
}
