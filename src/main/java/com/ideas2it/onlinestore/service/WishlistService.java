/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service;

import java.util.List;
import com.ideas2it.onlinestore.dto.WishlistDTO;
import com.ideas2it.onlinestore.model.Product;
import com.ideas2it.onlinestore.model.Wishlist;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * Interface for WishlistService.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
public interface WishlistService {

    /**
     * Create wishlist to the user using given wishlist details.
     * if the given details is not valid throws OnlineStoreException.
     *
     * @param wishlist                details of the wishlist.
     * @throws OnlineStoreException   occur when given wishlist details are not valid.
     */
    WishlistDTO createWishlist(WishlistDTO wishlist) throws OnlineStoreException;

    /**
     * Get the wishlist using given wishlist id.
     * if the given wishlist id is not exists throws OnlineStoreException
     * otherwise returns wishlist object.
     *
     * @param id                      id of the wishlist.
     * @return Wishlist               details of the wishlist.
     * @throws OnlineStoreException   occur when given wishlist id is not exists.
     */
    Wishlist getWishlistById(long id) throws OnlineStoreException;

    /** Add product to the user wishlist using the product id
     * if the product is added return String otherwise throws OnlineStoreException
     *
     * @param productId                 product id of the product.
     * @throws OnlineStoreException     occur when wishlist or product is not found.
     */
    String addProductToWishlist(long productId) throws OnlineStoreException;

    /**
     * Remove product from the user wishlist using wishlist and product id.
     * if the product is removed return String otherwise throws OnlineStoreException.
     *
     * @param productId                 product id of the product.
     * @throws OnlineStoreException     occur when wishlist or product is not found.
     */
    String removeProductFromWishlist(long productId) throws OnlineStoreException;
    List<Product> getAllWishlistProducts() throws OnlineStoreException;
}
