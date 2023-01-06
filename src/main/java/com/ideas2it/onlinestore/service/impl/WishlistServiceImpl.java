/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ideas2it.onlinestore.dto.ProductDTO;
import com.ideas2it.onlinestore.dto.WishlistDTO;
import com.ideas2it.onlinestore.model.Product;
import com.ideas2it.onlinestore.model.Wishlist;
import com.ideas2it.onlinestore.repository.WishlistRepository;
import com.ideas2it.onlinestore.service.ProductService;
import com.ideas2it.onlinestore.service.WishlistService;
import com.ideas2it.onlinestore.util.configuration.JwtFilter;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * Service Implementation of Wishlist.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
@Service
public class WishlistServiceImpl implements WishlistService {
    private WishlistRepository wishlistRepository;
    private ProductService productService;
    private Logger logger = LogManager.getLogger(WishlistServiceImpl.class);
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public WishlistServiceImpl(WishlistRepository wishlistRepository, ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.productService = productService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WishlistDTO createWishlist(WishlistDTO wishlistDTO) throws OnlineStoreException {
        Wishlist wishlist = modelMapper.map(wishlistDTO, Wishlist.class);
        wishlist = wishlistRepository.save(wishlist);

        if (!(0 < wishlist.getId())) {
            logger.error(Constant.PROFILE_NOT_CREATED);
            throw new OnlineStoreException(Constant.PROFILE_NOT_CREATED, HttpStatus.NOT_ACCEPTABLE);
        }
        wishlistDTO = modelMapper.map(wishlist, WishlistDTO.class);
        logger.info(Constant.DETAILS_FETCHED_SUCCESSFULLY);
        return wishlistDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Wishlist getWishlistById(long id) throws OnlineStoreException {
        Wishlist wishlist = wishlistRepository.findById(id).orElse(null);

        if (null == wishlist) {
            logger.error(Constant.WISHLIST_NOT_FOUND);
            throw new OnlineStoreException(Constant.WISHLIST_NOT_FOUND, HttpStatus.NOT_FOUND);
        };
        logger.info(Constant.DETAILS_FETCHED_SUCCESSFULLY);
        return wishlist;
    }

    /**
     * Update the wishlist using given wishlist details.
     * if the details are not valid throws OnlineStoreException.
     *
     * @param wishlist                details of the wishlist.
     * @throws OnlineStoreException   occur when given wishlist details are not valid.
     */
    private Wishlist updateWishlist(Wishlist wishlist) throws OnlineStoreException{
        wishlist = wishlistRepository.save(wishlist);

        if (!(0 < wishlist.getId())) {
            logger.error(Constant.NOT_UPDATED);
            throw new OnlineStoreException(Constant.NOT_UPDATED, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        logger.info(Constant.UPDATED_SUCCESSFULLY);
        return wishlist;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addProductToWishlist(long productId) {
        long id = JwtFilter.getThreadLocal().get().getWishlist().getId();
        Wishlist wishlist = getWishlistById(id);
        ProductDTO productDTO = productService.getById(productId);
        Product product = modelMapper.map(productDTO, Product.class);

        if (null != wishlist) {
            List<Product> products = wishlist.getProducts();

            if (!(isProductAlreadyExists(products, productId))) {
                products.add(product);
                wishlist.setProducts(products);
                updateWishlist(wishlist);
                logger.info(Constant.PRODUCT_ADDED_SUCCESSFULLY);
                return Constant.PRODUCT_ADDED_SUCCESSFULLY;
            }
        }
        logger.error(Constant.WISHLIST_NOT_FOUND);
        throw new OnlineStoreException(Constant.WISHLIST_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    /**
     * Checks if the given product is already exists in the wishlist or not.
     * if product is already exists throws OnlineStoreException otherwise returns false.
     *
     * @param products       details of products exists in user wishlist.
     * @param productId      id of the product.
     * @return boolean       if product is not exists return false.
     */
    private boolean isProductAlreadyExists(List<Product> products, long productId) {
        List<Long> existingId = products.stream().map(Product::getId).toList();

        for (Long id: existingId) {
            if (id == productId) {
                logger.error(Constant.PRODUCT_ALREADY_EXISTS);
                throw new OnlineStoreException(Constant.PRODUCT_ALREADY_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR );
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String removeProductFromWishlist(long productId) throws OnlineStoreException {
        long id = JwtFilter.getThreadLocal().get().getWishlist().getId();
        Wishlist wishlist = getWishlistById(id);
        ProductDTO product = productService.getById(productId);

        if (null != wishlist) {
            if (null != product) {
                List<Product> products = wishlist.getProducts();

                if (!products.isEmpty()) {
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).getId() == productId) {
                            products.remove(products.get(i));
                            wishlist.setProducts(products);
                            updateWishlist(wishlist);
                            logger.info(Constant.REMOVED_SUCCESSFULLY);
                            return Constant.REMOVED_SUCCESSFULLY;
                        }
                    }
                }
                logger.error(Constant.PRODUCTS_NOT_EXISTS);
                throw new OnlineStoreException(Constant.PRODUCTS_NOT_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            logger.error(Constant.PRODUCT_NOT_FOUND);
            throw new OnlineStoreException(Constant.PRODUCT_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.error(Constant.WISHLIST_NOT_FOUND);
        throw new OnlineStoreException(Constant.WISHLIST_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public List<Product> getAllWishlistProducts() {
        long id = JwtFilter.getThreadLocal().get().getWishlist().getId();
        Wishlist wishlist = getWishlistById(id);

        if (!wishlist.getProducts().isEmpty()) {
            return wishlist.getProducts();
        }
        throw new OnlineStoreException(Constant.PRODUCTS_NOT_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
