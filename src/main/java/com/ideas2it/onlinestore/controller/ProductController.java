/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.onlinestore.dto.BrandDTO;
import com.ideas2it.onlinestore.dto.ProductDTO;
import com.ideas2it.onlinestore.dto.StockDTO;
import com.ideas2it.onlinestore.service.ProductService;
import com.ideas2it.onlinestore.service.StockService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Provides interaction between user and application.
 *
 * @author Sangeetha Ilangovan
 * @version 1.0
 * @since 12.12.2022
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductService productService;
	
	private StockService stockService;
	
	@Autowired
	private ProductController(ProductService productService, StockService stockService) {
		this.productService = productService;
		this.stockService = stockService;
	}

	/**
	 * Insert a product using the user input if it is valid otherwise it 
	 * throws exception.
	 * 
	 * @param product - the product to inserted
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while creating product.
	 */
	@PostMapping
	@ApiOperation(value = "Adds new product",
            notes = "Seller can Add new products",
            response = ProductDTO.class)
	private ResponseEntity<ProductDTO> add(@Valid @RequestBody ProductDTO product) {
		return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
	}

	/**
	 * Used to view all the product if products exists otherwise throws exception.
	 * 
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException if no products found.
	 */
	@GetMapping("/all")
	@ApiOperation(value = "Shows all the projects",
            notes = "User can view all the products available",
            response = ProductDTO.class)
	private ResponseEntity<List<ProductDTO>> getAll() {
		return new ResponseEntity<List<ProductDTO>>(productService.getAll(), HttpStatus.OK);
	}

	/**
	 * Used to view all the product if products exists by brand otherwise throws exception.
	 * 
	 * @param id - id of the brand
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while getting all  the products.
	 */
	@GetMapping("/brands")
	@ApiOperation(value = "Shows products by brand",
            notes = "User can view all the products available in a particular brand",
            response = ProductDTO.class)
	private ResponseEntity<Object> getByBrand(@ApiParam(name = "ID", value = "id of the brand") @RequestParam("id") Long brandId) {
		return new ResponseEntity<>(productService.getBrand(brandId), HttpStatus.OK);
	}

	/**
	 * Used to view all the product if products exists by category otherwise throws exception.
	 * 
	 * @param id - category id
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while getting all  the products.
	 */
	@GetMapping("/category")
	@ApiOperation(value = "Shows products by category",
    notes = "User can view all the products available in a particular category",
    response = ProductDTO.class)
	private ResponseEntity<Object> getByCategory(@ApiParam(name = "ID", value = "id of the category") @RequestParam("id") Long categoryId) {
		return new ResponseEntity<>(productService.getByCategory(categoryId), HttpStatus.OK);
	}

	/**
	 * Used to view all the product if products exists by sub category otherwise throws exception.
	 * 
	 * @param id - sub category id
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while getting all  the products.
	 */
	@GetMapping("/subcategory")
	@ApiOperation(value = "Shows products by sub category",
    notes = "User can view all the products available in a particular sub category",
    response = ProductDTO.class)
	private ResponseEntity<Object> getBySubCategory(@ApiParam(name = "ID", value = "id of the sub category") @RequestParam("id") Long subCategoryId) {
		return new ResponseEntity<>(productService.getBySubCategory(subCategoryId), HttpStatus.OK);
	}

	/**
	 * Updates the product by using the id of the product otherwise throws exception.
	 * 
	 * @param id - id of the product
	 * @param product - product to be updated.
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while updating the product.
	 */
	@PutMapping
	@ApiOperation(value = "Updates products by id",
    notes = "Seller can update the product available",
    response = ProductDTO.class)
	private ResponseEntity<ProductDTO> update(@ApiParam(name = "ID", value = "id of the product to be updated") @RequestParam("id") Long productId,
			@RequestBody ProductDTO product) {
		product.setId(productId);
		return new ResponseEntity<ProductDTO>(productService.updateProduct(product), HttpStatus.OK);
	}

	/**
	 * Used to view a product by using it's id if it exists otherwise throws exception.
	 * 
	 * @param id - id of the product
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while getting the product.
	 */
	@GetMapping
	@ApiOperation(value = "Shows product by id",
    notes = "User can view the product available by id",
    response = ProductDTO.class)
	private ResponseEntity<ProductDTO> getById(@ApiParam(name = "ID", value = "id of the product to be viewed") @RequestParam("id") Long productId) {
		return new ResponseEntity<ProductDTO>(productService.getById(productId), HttpStatus.OK);
	}

	/**
	 * Insert a brand using the user input if it is valid otherwise throws exception.
	 * 
	 * @param brand - brand to be inserted.
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while creating the brand.
	 */
	@PostMapping("/brand")
	@ApiOperation(value = "Adds brand",
    notes = "Admin can create a new brand",
    response = BrandDTO.class)
	private ResponseEntity<BrandDTO> addBrand(@Valid @RequestBody BrandDTO brand) {
		return new ResponseEntity<BrandDTO>(productService.addBrand(brand), HttpStatus.CREATED);
	}

	/**
	 * Used to view all the brand if brands exists otherwise throws exception.
	 * 
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while getting the brands.
	 */
	@GetMapping("/brand/all")
	@ApiOperation(value = "Shows all the brands",
    notes = "User can view all the brands",
    response = BrandDTO.class)
	private ResponseEntity<List<BrandDTO>> viewBrands() {
		return new ResponseEntity<List<BrandDTO>>(productService.getAllBrands(), HttpStatus.OK);
	}

	/**
	 * Updates the brand by using the id of the brand. otherwise throws exception
	 * 
	 * @param id - id of the brand
	 * @param brand - brand to be updated
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while updating the brand.
	 */
	@ApiOperation(value = "Updates the brand",
		    notes = "Admin can update the brand",
		    response = BrandDTO.class)
	@PutMapping("/brand")
	private ResponseEntity<BrandDTO> updateBrand(@ApiParam(name = "ID", value = "id of the brand to be updated") @RequestParam("id") Long brandId,
			@RequestBody BrandDTO brand) {		
        brand.setId(brandId);           
		return new ResponseEntity<BrandDTO>(productService.updateBrand(brand), HttpStatus.OK);
	}

	/**
	 * Used to view a brand by using it's id if it exists otherwise throws 
	 * exception.
	 * 
	 * @param id - id of the brand
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 * @throws OnlineStoreException - throws exception if something went wrong 
	 * while getting the brand.
	 */
	@GetMapping("/brand")
	@ApiOperation(value = "Shows the brand by id",
    notes = "User can view the brand available",
    response = BrandDTO.class)
	private ResponseEntity<BrandDTO> viewBrand(@ApiParam(name = "ID", value = "id of the brand to be viewed") @RequestParam("id") Long brandId) {
		return new ResponseEntity<BrandDTO>(productService.getBrand(brandId), HttpStatus.OK);
	}
	
	/**
	 * This method is used to get list of stock products if it exists otherwise it throws exception.
	 * 
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 */
	@GetMapping("/stock/all")
	@ApiOperation(value = "Shows all stocks",
    notes = "Seller or admin can view all the stocks available",
    response = StockDTO.class)
	private ResponseEntity<List<StockDTO>> getStocks() {
		return new ResponseEntity<>(stockService.getStockProducts(), HttpStatus.OK);
	}
	
	/**
	 * This method is used to get the stock product 
	 * by using stock id if it exist otherwise it throws exception.
	 * 
	 * @param id - id of the stock to be viewed
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 */
	@GetMapping("/stock")
	@ApiOperation(value = "Shows the stock by id",
    notes = "User can view the stock available by using id",
    response = StockDTO.class)
	private ResponseEntity<StockDTO> getStock(@ApiParam(name = "ID", value = "id of the stock to be viewed") @RequestParam("id") long id) {
		return new ResponseEntity<>(stockService.getStockProductById(id), HttpStatus.OK);
	}
	
	/**
	 * This method is used to get the stock product 
	 * by using seller if it exists otherwise it throws exception
	 * 
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 */
	@GetMapping("/stock/seller")
	@ApiOperation(value = "Shows the stocks by seller id",
    notes = "Seller or admin can view the stock available by using seller id",
    response = StockDTO.class)
	private ResponseEntity<List<StockDTO>> getAllStockBySeller() {
		return new ResponseEntity<>(stockService.getStockProductsBySeller(), HttpStatus.OK);
	}
	
	/**
	 * This method is used to delete the stock product 
	 * by using stock id.It throws exception if the particular stock does not exists.
	 * 
	 * @param id - id of the stock to be deleted
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 */
	@DeleteMapping("/stock")
	@ApiOperation(value = "Deletes the stock by id",
    notes = "Seller or admin can delete the stock available by using id",
    response = Boolean.class)
	private ResponseEntity<Boolean> deleteStock(@ApiParam(name = "ID", value = "id of the stock to be deleted") @RequestParam("id") long id) {
		return new ResponseEntity<>(stockService.deleteStock(id), HttpStatus.OK);
	}
	
	/**
	 * This method is used to get the stock product 
	 * by using stock id.It throws exception if the particular stock does not exists.
	 * 
	 * @param id - stock id whose stock is to updated
	 * @param stockDTO - stock to be updated
	 * @return ResponseEntity - represents the whole HTTP response: status code,
	 * headers, and body.
	 */
	@PutMapping("/stock")
	@ApiOperation(value = "Updates the stock by id",
    notes = "Seller or admin can update the stock available by using id",
    response = Boolean.class)
	private ResponseEntity<Boolean> updateStock(
			@ApiParam(name = "ID", value = "id of the stock to be updated") @RequestParam("id") long id, @RequestBody StockDTO stockDTO) {
		return new ResponseEntity<>(stockService.updateStock(id, stockDTO), HttpStatus.OK);
	}
}
