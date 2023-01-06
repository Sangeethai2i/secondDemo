/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 * 
 * This document is protected by copyright. No part of this document may be 
 * reproduced in any form by any means without prior written authorization of 
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.onlinestore.dto.BrandDTO;
import com.ideas2it.onlinestore.dto.ProductDTO;
import com.ideas2it.onlinestore.model.Brand;
import com.ideas2it.onlinestore.model.Product;
import com.ideas2it.onlinestore.model.Stock;
import com.ideas2it.onlinestore.repository.BrandRepository;
import com.ideas2it.onlinestore.repository.ProductRepository;
import com.ideas2it.onlinestore.service.CategoryService;
import com.ideas2it.onlinestore.service.ProductService;
import com.ideas2it.onlinestore.service.StockService;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;
import com.ideas2it.onlinestore.util.mapper.BrandMapper;
import com.ideas2it.onlinestore.util.mapper.ProductMapper;

/**
 * Provides various methods to get, insert, update and delete product
 * 
 * @author Sangeetha Ilangovan
 * @version 1.0
 * @since 12.12.2022
 */
@Service
public class ProductServiceImpl implements ProductService {

	private CategoryService categoryService;
	private BrandMapper brandMapper;
	private BrandRepository brandRepository;
	private ProductMapper productMapper;
	private ProductRepository productRepository;
	private StockService stockService;
	private Logger logger = LogManager.getLogger(ProductServiceImpl.class);

	@Autowired
	public ProductServiceImpl(CategoryService categoryService, StockService stockService,
			BrandRepository brandRepository, ProductRepository productRepository, ProductMapper productMapper,
			BrandMapper brandMapper) {
		this.categoryService = categoryService;
		this.stockService = stockService;
		this.brandRepository = brandRepository;
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.brandMapper = brandMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProductDTO addProduct(ProductDTO product) throws OnlineStoreException {
		int quantity = product.getQuantity();
		Date dateOfManufacture = product.getDateOfManufacture();
		Date dateOfExpire = product.getDateOfExpiry();

		if (dateOfManufacture.before(dateOfExpire)) {
			Product createdProduct = productRepository.findByNameAndDescription(product.getName(),
					product.getDescription());

			if (null != createdProduct) {
				product = productMapper.convertProductToProductDTO(productRepository.save(createdProduct));
			} else {
				product = productMapper.convertProductToProductDTO(
						productRepository.save(productMapper.convertProductDTOToProduct(product)));
			}
		} else {
			logger.error(Constant.INVALID_MANUFACTURE_EXPIRY_DATE);
			throw new OnlineStoreException(Constant.INVALID_MANUFACTURE_EXPIRY_DATE);
		}
		product.setDateOfManufacture(dateOfManufacture);
		product.setDateOfExpiry(dateOfExpire);
		product.setQuantity(quantity);
		logger.info(Constant.PRODUCT_ADDED_SUCCESSFULLY);
		return createStockForProduct(product);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProductDTO createStockForProduct(ProductDTO product) throws OnlineStoreException {
		if (null != product) {
			Stock stock = stockService.addStock(productMapper.convertProductDTOToProduct(product));

			if (null != stock) {
				logger.info(Constant.PRODUCT_ADDED_SUCCESSFULLY);
				return product;
			} else {
				logger.error(Constant.STOCK_CREATION_FAILED);
				throw new OnlineStoreException(Constant.STOCK_CREATION_FAILED);
			}
		} else {
			logger.error(Constant.PRODUCT_CREATION_FAILED);
			throw new OnlineStoreException(Constant.PRODUCT_CREATION_FAILED);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDTO> getAll() throws OnlineStoreException {
		List<ProductDTO> availabeProducts = new ArrayList<ProductDTO>();
		List<Product> products = productRepository.findAll();

		if (!products.isEmpty()) {

			for (Product product : products) {

				if (!product.isDeleted()) {
					availabeProducts.add(productMapper.convertProductToProductDTO(product));
				}
			}
		} else {
			logger.error(Constant.PRODUCTS_NOT_EXISTS);
			throw new OnlineStoreException(Constant.PRODUCTS_NOT_EXISTS);
		}
		logger.info(Constant.GETTING_PRODUCTS_SUCCESS);
		return availabeProducts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProductDTO updateProduct(ProductDTO product) throws OnlineStoreException {

		if (!getAll().isEmpty()) {

			if (null != getById(product.getId())) {
				product = productMapper.convertProductToProductDTO(
						productRepository.save(productMapper.convertProductDTOToProduct(product)));

				if (null == product) {
					logger.error(Constant.PRODUCT_UPDATION_FAILED);
					throw new OnlineStoreException(Constant.PRODUCT_UPDATION_FAILED);
				}
			}
		} else {
			logger.error(Constant.PRODUCTS_NOT_EXISTS);
			throw new OnlineStoreException(Constant.PRODUCTS_NOT_EXISTS);
		}
		logger.info(Constant.PRODUCT_UPDATION_SUCCESS);
		return product;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProductDTO getById(Long productId) throws OnlineStoreException {
		Product product = productRepository.findById(productId).orElse(null);

		if (null == product || product.isDeleted()) {
			logger.error(Constant.PRODUCT_NOT_FOUND);
			throw new OnlineStoreException(Constant.PRODUCT_NOT_FOUND);
		}
		logger.info(Constant.GETTING_PRODUCT_SUCCESS);
		return productMapper.convertProductToProductDTO(product);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDTO> getByCategory(Long categoryId) throws OnlineStoreException {
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		List<Product> availableProducts = new ArrayList<Product>();

		if (!categoryService.getCategories().isEmpty()) {
			
			if (!getAll().isEmpty()) {
				availableProducts = productRepository.findByCategory(categoryId);

				if (!availableProducts.isEmpty()) {

					for (Product product : availableProducts) {
						products.add(productMapper.convertProductToProductDTO(product));
					}
				} else {
					logger.error(Constant.NO_PRODUCT_FOUND_IN_CATEGORY);
					throw new OnlineStoreException(Constant.NO_PRODUCT_FOUND_IN_CATEGORY);
				}
			}
		} else {
			logger.error(Constant.NO_CATEGORY_FOUND);
			throw new OnlineStoreException(Constant.NO_CATEGORY_FOUND);
		}
		logger.info(Constant.GETTING_PRODUCTS_SUCCESS);
		return products;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDTO> getBySubCategory(Long subCategoryId) throws OnlineStoreException {
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		List<Product> availableProducts = new ArrayList<Product>();

		if (!getAll().isEmpty()) {
			availableProducts = productRepository.findBySubCategory(subCategoryId);

			if (!availableProducts.isEmpty()) {

				for (Product product : availableProducts) {
					products.add(productMapper.convertProductToProductDTO(product));
				}
			} else {
				logger.error(Constant.NO_PRODUCT_FOUND_IN_SUBCATEGORY);
				throw new OnlineStoreException(Constant.NO_PRODUCT_FOUND_IN_SUBCATEGORY);
			}
		}
		logger.info(Constant.GETTING_PRODUCTS_SUCCESS);
		return products;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProductDTO> getByBrand(Long brandId) throws OnlineStoreException {
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		List<Product> availableProducts = new ArrayList<Product>();

		if (!getAllBrands().isEmpty() && !getAll().isEmpty()) {

			if (null != getBrand(brandId)) {
				availableProducts = productRepository.findByBrand(brandId);

				if (!availableProducts.isEmpty()) {

					for (Product product : availableProducts) {
						products.add(productMapper.convertProductToProductDTO(product));
					}
				} else {
					logger.error(Constant.NO_PRODUCT_FOUND_IN_BRAND);
					throw new OnlineStoreException(Constant.NO_PRODUCT_FOUND_IN_BRAND);
				}
			}
		} else {
			logger.error(Constant.NO_PRODUCT_BRAND_FOUND);
			throw new OnlineStoreException(Constant.NO_PRODUCT_BRAND_FOUND);
		}
		logger.info(Constant.GETTING_PRODUCTS_SUCCESS);
		return products;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BrandDTO addBrand(BrandDTO brand) {

		if (isBrandUnique(brand)) {
			brand = brandMapper.convertBrandToBrandDTO(brandRepository.save(brandMapper.convertBrandDTOToBrand(brand)));

			if (null == brand) {
				logger.error(Constant.BRAND_CREATION_FAILED);
				throw new OnlineStoreException(Constant.BRAND_CREATION_FAILED);
			}
		} else {
			logger.error(Constant.DUPLICATE_BRAND_NAME);
			throw new OnlineStoreException(Constant.DUPLICATE_BRAND_NAME);
		}
		logger.info(Constant.BRAND_CREATION_SUCCESS);
		return brand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BrandDTO> getAllBrands() throws OnlineStoreException {
		List<BrandDTO> availableBrands = new ArrayList<BrandDTO>();
		List<Brand> brands = brandRepository.findAll();

		if (!brands.isEmpty()) {

			for (Brand brand : brands) {

				if (!brand.isDeleted()) {
					availableBrands.add(brandMapper.convertBrandToBrandDTO(brand));
				}
			}
		} else {
			logger.error(Constant.BRANDS_NOT_FOUND);
			throw new OnlineStoreException(Constant.BRANDS_NOT_FOUND);
		}
		logger.info(Constant.BRANDS_FOUND);
		return availableBrands;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BrandDTO updateBrand(BrandDTO brand) throws OnlineStoreException {

		if (!getAllBrands().isEmpty()) {

			if (null != getBrand(brand.getId())) {

				if (isBrandUnique(brand)) {
					brand = brandMapper
							.convertBrandToBrandDTO(brandRepository.save(brandMapper.convertBrandDTOToBrand(brand)));
					if (null == brand) {
						logger.error(Constant.BRAND_UPDATION_FAILED);
						throw new OnlineStoreException(Constant.BRAND_UPDATION_FAILED);
					}
				} else {
					logger.error(Constant.DUPLICATE_BRAND_NAME);
					throw new OnlineStoreException(Constant.DUPLICATE_BRAND_NAME);
				}
			}
		}
		logger.info(Constant.BRAND_UPDATION_SUCCESS);
		return brand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBrandUnique(BrandDTO brand) {
		return null == brandRepository.findByName(brand.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BrandDTO getBrand(Long brandId) throws OnlineStoreException {
		Brand brand = brandRepository.findById(brandId).orElse(null);

		if (null == brand || brand.isDeleted()) {
			logger.error(Constant.BRAND_NOT_FOUND);
			throw new OnlineStoreException(Constant.BRAND_NOT_FOUND);
		}
		logger.info(Constant.BRAND_FOUND);
		return brandMapper.convertBrandToBrandDTO(brand);
	}
}
