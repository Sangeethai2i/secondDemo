/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service;

import java.util.List;

import com.ideas2it.onlinestore.dto.BrandDTO;
import com.ideas2it.onlinestore.dto.ProductDTO;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * All the operations like Create, update, delete, view, validation and other
 * operations are performed.
 *
 * @author Sangeetha Ilangovan
 * @version 1.0
 * @since 12.12.2022
 */
public interface ProductService {
	
	/**
	 * Create a product and returns it if it created otherwise it throws 
	 * exception.
	 * 
	 * @param product - product to be inserted.
	 * @return product if it is created otherwise null.
	 * @throws OnlineStoreException - throws exception if anything went wrong
	 * while creating product.
	 */
	ProductDTO addProduct(ProductDTO product) throws OnlineStoreException;

	/**
	 * Used to get all the products.It throws exception if no products found.
	 * 
	 * @return list of products
	 * @throws OnlineStoreException - throws exception if anything went wrong
	 * while getting all the products.
	 */
	List<ProductDTO> getAll() throws OnlineStoreException;

	/**
	 * Used to view a product by id if it exists otherwise it throws
	 * exception.
	 * 
	 * @param productId - id of the product
	 * @return product if it exists otherwise it throws exception.
	 * @throws OnlineStoreException if product is not found.
	 */
	ProductDTO getById(Long productId) throws OnlineStoreException;
	
	/**
	 * Used to view a list of product by id if it exists by category otherwise 
	 * it throws exception.
	 * 
	 * @param categoryId - category id
	 * @return list of product.
	 * @throws OnlineStoreException if product is not found.
	 */
	List<ProductDTO> getByCategory(Long categoryId) throws OnlineStoreException;
	
	/**
	 * Used to view a list of product by id if it exists by sub category 
	 * otherwise it throws exception.
	 * 
	 * @param subCategoryId - sub category id
	 * @return list of product.
	 * @throws OnlineStoreException if product is not found.
	 */
    List<ProductDTO> getBySubCategory(Long subCategoryId) throws OnlineStoreException;
	
	/**
	 * Used to view a list of product by brand if it exists by brand otherwise
	 * it throws exception.
	 * 
	 * @param brandId - id of the brand
	 * @return list of product.
	 * @throws OnlineStoreException if product is not found.
	 */
	List<ProductDTO> getByBrand(Long brandId) throws OnlineStoreException ;
	
	/**
	 * Create a brand and returns it if it is created otherwise it returns null.
	 * 
	 * @param brand - brand to be inserted.
	 * @return brand if it is created otherwise null;
	 */
	BrandDTO addBrand(BrandDTO brand);

	/**
	 * Used to get all the brands if brands exists otherwise it throws exception.
	 * 
	 * @return list of brands
	 * @throws OnlineStoreException if brands are not found.
	 */
	List<BrandDTO> getAllBrands() throws OnlineStoreException;

	/**
	 * Updates brand and brand based on whether the brand is 
	 * updated or not.
	 * 
	 * @param  - brand to be updated
	 * @return brand based on whether the brand is updated or not.
	 * @throws OnlineStoreException if something went wrong while updating brand.
	 */
	BrandDTO updateBrand(BrandDTO brand) throws OnlineStoreException;

	/**
	 * Used to view a brand by id if it exists otherwise it throws
	 * exception.
	 * 
	 * @param brandId - id of the brand
	 * @return brand if it exists.
	 * @throws OnlineStoreException if brand not found.
	 */
	BrandDTO getBrand(Long brandId) throws OnlineStoreException;

	/**
	 * Checks and returns true or false based on whether the brand name is
	 * unique or not.
	 * 
	 * @param brand - brand to checked whether it is unique or not.
	 * @return true or false based on whether the brand exists by name or not
	 */
	boolean isBrandUnique(BrandDTO brand);

	/**
	 * Updates product and returns it based on whether the product is 
	 * updated or not.
	 * 
	 * @param product - product to be updated.4
	 * @return product based on whether the product is updated or not.
	 * @throws OnlineStoreException if the product is not found or it is not 
	 * updated.
	 */
	ProductDTO updateProduct(ProductDTO product) throws OnlineStoreException;
	
	/**
	 * Checks whether product is created and creates stock if product is 
	 * created otherwise it throws exception.
	 * 
	 * @param product
	 * @return product which is inserted
	 * @throws OnlineStoreException if product and stock is not created.
	 */
	ProductDTO createStockForProduct(ProductDTO product) throws OnlineStoreException;
}
