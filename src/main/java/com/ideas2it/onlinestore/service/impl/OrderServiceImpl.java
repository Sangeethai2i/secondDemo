/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ideas2it.onlinestore.dto.AddressDTO;
import com.ideas2it.onlinestore.dto.OrderDTO;
import com.ideas2it.onlinestore.model.Address;
import com.ideas2it.onlinestore.model.Cart;
import com.ideas2it.onlinestore.model.CartProduct;
import com.ideas2it.onlinestore.model.Order;
import com.ideas2it.onlinestore.model.OrderProduct;
import com.ideas2it.onlinestore.model.User;
import com.ideas2it.onlinestore.repository.OrderRepository;
import com.ideas2it.onlinestore.service.AddressService;
import com.ideas2it.onlinestore.service.CartProductService;
import com.ideas2it.onlinestore.service.OrderService;
import com.ideas2it.onlinestore.util.OrderUtil;
import com.ideas2it.onlinestore.util.configuration.JwtFilter;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.constants.OrderStatus;
import com.ideas2it.onlinestore.util.customException.DataNotFoundException;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * 
 * @author admin
 *
 */
@Service
public class OrderServiceImpl implements OrderService{
	
	private final AddressService addressService;
	
	private final OrderRepository orderRepository;
	
	private final CartProductService cartProductService;
	
	private final OrderUtil orderUtil;
	
	private final ModelMapper mapper = new ModelMapper();
	
    private Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository,
			AddressService addressService, CartProductService
			cartProductService, OrderUtil orderUtil) {
		
		this.orderRepository = orderRepository;
		this.addressService = addressService;
		this.cartProductService = cartProductService;
		this.orderUtil = orderUtil;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderDTO placeOrder(long addressId) {
		User user = JwtFilter.threadLocal.get();
		AddressDTO addressDTO = this.addressService.getAddressById(addressId);

		if(null != user && null != addressDTO) {
			Cart cart = user.getCart();
			List<CartProduct> cartProducts = cart.getCartProducts();

			if(!cartProducts.isEmpty() ) {
				Order order = checkoutCart(user, cartProducts, addressDTO);
				for (CartProduct cartProduct : cartProducts) {
					cartProduct.setDeleted(true);
					cartProductService.saveCartProduct(cartProduct);
				}
				OrderDTO orderDTO = mapper.map(order, OrderDTO.class);
				logger.info(Constant.ORDER_SUCCESSFUL);
				return orderDTO;
			} else {
				logger.error(Constant.EMPTY_CART);
				throw new OnlineStoreException(Constant.EMPTY_CART, HttpStatus.NO_CONTENT);
			}
		} else {
			logger.error(Constant.ADDRESS_NOT_ADDED);
			throw new OnlineStoreException(Constant.ADDRESS_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
			
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderDTO getOrderById(long orderId) {
		Order order = this.orderRepository.findById(orderId)
				.orElseThrow(()-> new OnlineStoreException("Order not found. ID: " +orderId, HttpStatus.NO_CONTENT));
		logger.error(Constant.ORDER_FETCHED);
		return mapper.map(order, OrderDTO.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override	
	public List<OrderDTO> getAllOrders() {
		User user = JwtFilter.threadLocal.get();
		List<Order> orders = user.getOrders();		
		List<OrderDTO> orderDTOs = orders.stream()
				.map(order -> mapper.map(order, OrderDTO.class))
				.collect(Collectors.toList());
		logger.info(Constant.ORDERS_FETCHED);
		return orderDTOs;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateOrder(long orderId) {
		boolean orderCancellation = false;
		Order order = this.orderRepository.findById(orderId)
				.orElseThrow(()-> new OnlineStoreException(Constant.ORDER_NOT_FOUND +orderId, HttpStatus.NO_CONTENT));
		
		if (null != order) {
			List<OrderProduct> orderProducts = order.getOrderProducts();
			for (OrderProduct orderProduct : orderProducts) {
				orderProduct.setDeleted(true);
			}
			order.setOrderProducts(orderProducts);
			order.setStatus(OrderStatus.CANCELLED);
			this.orderRepository.save(order);
			orderUtil.increaseStocks(orderProducts);
			orderCancellation = true;
			logger.info(Constant.ORDER_UPDATED);
			return orderCancellation;
		} else {
			logger.error(Constant.ORDER_NOT_FOUND);
			throw new DataNotFoundException(Constant.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 
	 * @param user
	 * @param cartProducts
	 * @param addressDTO
	 * @return
	 */
	private Order checkoutCart(User user, List<CartProduct> cartProducts, AddressDTO addressDTO) {
		Order order = new Order();
		order.setUser(user);
		order.setAddress(mapper.map(addressDTO, Address.class));
		order.setAmount(updateCartTotal(cartProducts));
		order.setOrderProducts(orderUtil.getOrderProductList(cartProducts));
		order.setDate(LocalDate.now());
		order.setStatus(OrderStatus.SUCCESSFUL);
		this.orderRepository.save(order);
		orderUtil.decreaseStocks(orderUtil.getOrderProductList(cartProducts));
		return order;
	}
	
	/**
	 * This methods calculates the current cart total by fetching
	 * the price of each product that has been added to the cart.
	 * 
	 * @param cartProducts(List of products that have been added 
	 * to the cart)
	 * @return double(current value of the cart)
	 */
	private double updateCartTotal(List<CartProduct> cartProducts) {
		double total = 0;
		cartProducts = cartProducts.stream()
				.filter(cartProduct -> (!cartProduct.isDeleted() ))
				.collect(Collectors.toList());
		for (CartProduct cartProduct : cartProducts) {
			total = total + (cartProduct.getProduct().getPrice() * cartProduct.getQuantity());
		}
		logger.info(Constant.CART_TOTAL_UPDATED);
		return total;
	}
}
