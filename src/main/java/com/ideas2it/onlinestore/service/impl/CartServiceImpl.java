/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ideas2it.onlinestore.dto.CartDTO;
import com.ideas2it.onlinestore.dto.CartProductDTO;
import com.ideas2it.onlinestore.model.Cart;
import com.ideas2it.onlinestore.model.CartProduct;
import com.ideas2it.onlinestore.model.Product;
import com.ideas2it.onlinestore.model.User;
import com.ideas2it.onlinestore.repository.CartRepository;
import com.ideas2it.onlinestore.service.CartService;
import com.ideas2it.onlinestore.service.ProductService;
import com.ideas2it.onlinestore.service.StockService;
import com.ideas2it.onlinestore.util.configuration.JwtFilter;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.customException.InsufficientStockException;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;
import com.ideas2it.onlinestore.util.customException.DataNotFoundException;


/**
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */
@Service
public class CartServiceImpl implements CartService {
	
	private CartRepository cartRepository;
	
	private ProductService productService;
	
	private StockService stockService;
	
    private Logger logger = LogManager.getLogger(CartServiceImpl.class);

	private final ModelMapper mapper = new ModelMapper();
	
	@Autowired
	public CartServiceImpl(CartRepository cartRepository, 
			ProductService productService,
			StockService stockService) {
		this.cartRepository = cartRepository;
		this.productService = productService;
		this.stockService = stockService;
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CartDTO createCart(CartDTO cartDTO) {
		Cart cart = cartRepository.save(mapper.map(cartDTO, Cart.class));
		logger.info(Constant.CART_CREATED);
		return mapper.map(cart, CartDTO.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CartDTO addToCart(long productId, int quantity) throws OnlineStoreException {
		User user = JwtFilter.threadLocal.get();
		Product product = mapper.map(productService.getById(productId), Product.class);
		
		if (null != user) {
			Cart cart = user.getCart();
			List<CartProduct> cartProducts = cart.getCartProducts();
			
			for (CartProduct cartProduct : cartProducts) {
				
				if(Objects.equals(cartProduct.getProduct().getId(), product.getId())) {
					quantity = quantity + cartProduct.getQuantity();
				}
			}
			int stockQuantity = stockService.getQuantity(product.getName());
			
			if (stockQuantity > quantity) {
				CartProduct cartProduct = new CartProduct(quantity, product);
				cartProducts.add(cartProduct);
				cart.setCartProducts(cartProducts);
				cart.setCartTotal(updateCartTotal(cartProducts));
				CartDTO cartDTO = convertCartEntityToDTO(cartRepository.save(cart));
				logger.info(Constant.ADD_TO_CART);
				return cartDTO;
			} else {
				logger.info(Constant.INSUFFICIENT_STOCK);
				throw new InsufficientStockException(Constant.INSUFFICIENT_STOCK, HttpStatus.INSUFFICIENT_STORAGE);
			}
		} else {
			logger.error(Constant.USER_NOT_FOUND);
			throw new DataNotFoundException(Constant.USER_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CartDTO removeFromCart(long productId) {
		User user = JwtFilter.threadLocal.get();
		
		if (null != user) {			
			Cart cart = user.getCart();
			List<CartProduct> cartProducts = new CopyOnWriteArrayList<>();
			cartProducts.addAll(cart.getCartProducts());
			
			if (!cartProducts.isEmpty()) {
				
				for (CartProduct cartProduct : cartProducts) {
					
					if (Objects.equals(cartProduct.getProduct().getId(), productId)) {
						cartProduct.setDeleted(true);
					}
				}
				cart.setCartProducts(cartProducts);
				CartDTO cartDTO = convertCartEntityToDTO(cartRepository.save(cart));
				logger.info(Constant.REMOVE_FROM_CART);
				return cartDTO;
				
			} else {
				logger.info(Constant.EMPTY_CART);
				throw new OnlineStoreException(Constant.EMPTY_CART, HttpStatus.NO_CONTENT); 
			}
		} else {
			logger.error(Constant.USER_NOT_FOUND);
			throw new OnlineStoreException(Constant.USER_NOT_FOUND, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CartDTO getCart() throws OnlineStoreException {
		User user = JwtFilter.threadLocal.get();
		Cart cart = user.getCart();
		cart.setCartTotal(updateCartTotal(cart.getCartProducts()));
		CartDTO cartDTO = mapper.map(cart, CartDTO.class);
		logger.info(Constant.CART_FETCHED);
		return cartDTO;		
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
		logger.info(Constant.REMOVE_FROM_CART);
		return total;
	}
	
	/**
	 * This method takes a cart object and converts it into 
	 * a cartDTO object.
	 * @param cart(cart object)
	 * @return CartDTO
	 */
    public CartDTO convertCartEntityToDTO(Cart cart) {
    	CartDTO cartDTO = new CartDTO();
    	cartDTO.setId(cart.getId());
    	List<CartProduct> cartProducts = cart.getCartProducts().stream()
    			.filter(cartProduct -> (!cartProduct.isDeleted()))
    			.collect(Collectors.toList());
    	List<CartProductDTO> cartProductDTOs = new ArrayList<>();
    	cartDTO.setCartTotal(updateCartTotal(cartProducts));
    	
    	for (CartProduct cartProduct : cartProducts) {
			CartProductDTO cartProductDTO = new CartProductDTO();
			cartProductDTO.setId(cartProduct.getId());
			cartProductDTO.setQuantity(cartProduct.getQuantity());
			cartProductDTO.setProduct(productService.getById(cartProduct.getProduct().getId()));
			cartProductDTOs.add(cartProductDTO);
		}    	
    	cartDTO.setCartProducts(cartProductDTOs);
    	return cartDTO;
    }
	
}
