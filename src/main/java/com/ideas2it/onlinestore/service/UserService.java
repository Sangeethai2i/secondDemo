/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service;

import java.util.List;

import com.ideas2it.onlinestore.dto.AddressDTO;
import com.ideas2it.onlinestore.dto.UserDTO;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Interface for UserService
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
public interface UserService extends UserDetailsService {

    /**
     * User is created once the given email id and mobileNumber is checked.
     * if the given email id and mobileNumber is already exists throws OnlineStoreException
     * otherwise returns user object.
     *
     * @param user                     details of the user.
     * @return String                  status message.
     * @throws OnlineStoreException    occur when given details are not valid.
     */
    UserDTO createUser(UserDTO user) throws OnlineStoreException;

    /**
     * Get the user using given user id.
     * if the given user id is not valid throws OnlineStoreException otherwise
     * returns user object.
     *
     * @param id                        id of the user.
     * @return UserDTO                     details of the user.
     * @throws OnlineStoreException     occur when given id is not exists.
     */
    UserDTO getUserById(long id) throws OnlineStoreException;

    /**
     * Update the user details using given details.
     * if the given email id and mobileNumber is already exists then match with existing user
     * and given user to find the two user is same. if same update the details otherwise throws
     * OnlineStoreException.
     *
     * @param user      details of the user.
     */
    UserDTO updateUser(UserDTO user) throws OnlineStoreException;

    /**
     * Delete the user using given user id.
     * if the given user id is not exist throws OnlineStoreException otherwise delete the user.
     *
     * @throws OnlineStoreException    occur when given user is not exists.
     */
    String deleteUser() throws OnlineStoreException;

    /**
     * Get all registered user details.
     * if the users are not exists throws OnlineStoreException otherwise returns user details.
     *
     * @return List<User>              details of the registered user.
     * @throws OnlineStoreException    occur when users are not exists.
     */
    List<UserDTO> getAllUser() throws OnlineStoreException;
}
