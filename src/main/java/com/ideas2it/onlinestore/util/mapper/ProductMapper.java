/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.ProductDTO;
import com.ideas2it.onlinestore.model.Product;

/**
 * Converts product.
 *
 * @author Sangeetha Ilangovan
 * @version 1.0
 * @since 21.12.2022
 */
@Component
public class ProductMapper {
	
	@Autowired
	private BrandMapper brandMapper;

	/**
	 * Converts productDTO to product
	 * 
	 * @param productDTO
	 * @return product
	 */
	public static Product convertProductDTOToProduct(ProductDTO productDTO) {
		Product product = null;//new Product();
		
		if (0 != productDTO.getId()) {
		    product.setId(productDTO.getId());
		}
		//product.setBrand(brandMapper.convertBrandDTOToBrand(productDTO.getBrand()));
		product.setCategory(productDTO.getCategory());
		product.setSubCategory(productDTO.getSubCategory());
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setDateOfManufacture(productDTO.getDateOfManufacture());
		product.setDateOfExpire(productDTO.getDateOfExpiry());
		product.setQuantity(productDTO.getQuantity());
		return product;
	}
	
	/**
	 * Converts product to productDTO
	 * 
	 * @param product
	 * @return productDTO
	 */
	public static ProductDTO convertProductToProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		//productDTO.setBrand(brandMapper.convertBrandToBrandDTO(product.getBrand()));
		productDTO.setCategory(product.getCategory());
		productDTO.setSubCategory(product.getSubCategory());
		productDTO.setDescription(product.getDescription());
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());
		productDTO.setDateOfManufacture(product.getDateOfManufacture());
		productDTO.setDateOfExpiry(product.getDateOfExpire());
		productDTO.setQuantity(product.getQuantity());
		return productDTO;
	}
}
