/* 
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.util.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.StockDTO;
import com.ideas2it.onlinestore.model.Stock;

/**
 * This class convert entity to DTO, DTO to entity
 * 
 * @author arunkumar
 * @version 1.0
 */
@Component
public class StockMapper {
	
	private UserMapper userMapper;
	private ProductMapper productMapper;
	
	@Autowired 
	public StockMapper(UserMapper userMapper, ProductMapper productMapper) {
		super();
		this.userMapper = userMapper;
		this.productMapper = productMapper;
	}
	 
	/**
	 * <p>
	 * This method is used change the DTO object to 
	 * entity object. This DTO is comes from stock service 
	 * and create one empty object for stock and 
	 * get the value from stock DTO object and set the value 
	 * into stock then It will return stock object
	 * whenever this method was called. 
	 * </p>
	 * 
	 * @param stockDTO
	 * @return stock 
	 */
	public Stock convertDTOToEntity(StockDTO stockDTO) {
		Stock stock = new Stock();
		stock.setId(stockDTO.getId());
		stock.setProductName(stockDTO.getProductName());
		stock.setQuantity(stockDTO.getQuantity());
		stock.setDateOfManufacture(stockDTO.getDateOfManufacture());
		stock.setDateOfExpire(stockDTO.getDateOfExpire());
		stock.setSeller(userMapper.convertUserDTOToDAO(stockDTO.getSeller()));
		stock.setProduct(productMapper.convertProductDTOToProduct(stockDTO.getProduct()));
		return stock;
	}
	
	/**
	 * <p>
	 * This method is used change the entity object to 
	 * DTO object. This entity is comes from stock service 
	 * and create one empty object for stock DTO and 
	 * get the value from stock object and set the value 
	 * into stock DTO then It will return stock DTO object
	 * whenever this method was called.
	 * </p>
	 * 
	 * @param stock
	 * @return stockDTO
	 */
	public StockDTO convertEntityToDTO(Stock stock) {
		StockDTO stockDTO = new StockDTO();
		stockDTO.setId(stock.getId());
		stockDTO.setProductName(stock.getProductName());
		stockDTO.setQuantity(stock.getQuantity());
		stockDTO.setDateOfManufacture(stock.getDateOfManufacture());
		stockDTO.setDateOfExpire(stock.getDateOfExpire());
		stockDTO.setSeller(userMapper.convertUserDAOToDTO(stock.getSeller()));
		stockDTO.setProduct(productMapper.convertProductToProductDTO(stock.getProduct()));
		return stockDTO;
	}
	
	/**
	 * <p>
	 * This method is used change the list of entity object to 
	 * list of DTO object. This list of stock is comes from stock service 
	 * and create one empty list for stock DTO and 
	 * add the value from list of stock object to list of stock DTO object 
	 * then It will return list of stock DTO object
	 * whenever this method was called.  
	 * </p>
	 * 
	 * @param stocks - list of stock products
	 * @return stockDTOs - list of stockDTO products
	 */
	public List<StockDTO> convertEntityToDTO(List<Stock> stocks) {
		List<StockDTO> stockDTOs = new ArrayList<>();
		for (Stock stock : stocks) {
			stockDTOs.add(convertEntityToDTO(stock));
		}
		return stockDTOs;
	}
}
