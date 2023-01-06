/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service;

import com.ideas2it.onlinestore.dto.AddressDTO;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * Interface for Address service
 *
 * @author Gokul V
 * @version 1.0
 * @since 2022-12-17
 */
public interface AddressService {

    /**
     * Add address to the user using given user id.
     * if the given user id is not exists throws OnlineStoreException.
     *
     * @param address                 details of the address.
     * @throws OnlineStoreException   occur when given user id or address is not valid.
     */
    String addAddress(AddressDTO address) throws OnlineStoreException;

    /**
     * Delete the address from the user using given user details.
     * if the given user or address id is not exists throws OnlineStoreException.
     *
     * @param id                      id of the address.
     * @throws OnlineStoreException   occur when given user or address id is not exists.
     */
    String deleteAddress(long id) throws OnlineStoreException;
    /**
     * Get the address using given address id.
     * if the given address id is not valid throws OnlineStoreException otherwise
     * returns address object.
     *
     * @param id                        id of the address.
     * @return AddressDTO               details of the address.
     * @throws OnlineStoreException     occur when given id is not exists.
     */
    AddressDTO getAddressById(long id) throws OnlineStoreException;
}
