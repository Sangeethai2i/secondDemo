/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.controller;

import java.util.List;

import com.ideas2it.onlinestore.util.customAnnotations.CustomRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.onlinestore.model.Product;
import com.ideas2it.onlinestore.service.WishlistService;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * Controller for Wishlist
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    private WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    /**
     * Add product to the user wishlist using the product id
     * if the product is added return Status message otherwise
     * throws OnlineStoreException
     *
     * @param productId                 product id of the product.
     * @return ResponseEntity<String>   status message.
     * @throws OnlineStoreException     occur when wishlist or product is not found.
     */
    @PostMapping("/add")
    @ApiOperation(value = "Add Product To Wishlist",
            notes = "User can Add liked product to his wishlist",
            response = String.class)
    public ResponseEntity<String> addProductToWishlist(@ApiParam(name = "ID", value = "ID of the Product")
                                                           @RequestParam("id") long productId) throws OnlineStoreException {
        return ResponseEntity.status(HttpStatus.OK).body(wishlistService.addProductToWishlist(productId));
    }

    /**
     * Remove product from the user wishlist using product id
     * if the product is removed return Status message otherwise
     * throws OnlineStoreException.
     *
     * @param productId                 product id of the product.
     * @return ResponseEntity<String>   status message.
     * @throws OnlineStoreException     occur when wishlist or product is not found.
     */
    @PostMapping("/remove")
    @ApiOperation(value = "Remove Product From Wishlist",
            notes = "User can remove product from his wishlist",
            response = String.class)
    public ResponseEntity<String> removeProductFromWishlist(@ApiParam(name = "ID", value = "ID of the Product")
                                                                @RequestParam("id") long productId) throws OnlineStoreException {
        return ResponseEntity.status(HttpStatus.OK).body(wishlistService.removeProductFromWishlist(productId));
    }

    /**
     * Get all products from the user wishlist.
     * if the given wishlist id is not valid or products not exists throws OnlineStoreException.
     *
     * @return ResponseEntity<List<Product>>       liked products in user wishlist.
     * @throws OnlineStoreException                occur when wishlist or products are not exists.
     */
    @GetMapping
    @ApiOperation(value = "View All Wishlist Products",
            notes = "View All wishlist products in the user wishlist",
            response = Product.class)
    public ResponseEntity<List<Product>> getWishlistProducts() throws OnlineStoreException {
        return ResponseEntity.status(HttpStatus.OK).body(wishlistService.getAllWishlistProducts());
    }
}
