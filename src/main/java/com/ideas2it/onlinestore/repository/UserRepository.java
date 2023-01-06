/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ideas2it.onlinestore.model.User;

/**
 * Repository of User
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by given email
     *
     * @param email    email of the user.
     * @return User    return a user.
     */
    User findByEmail(String email);

    /**
     * Find user by given mobileNumber.
     *
     * @param mobileNumber     mobileNumber of the user.
     * @return User            return user.
     */
    User findByMobileNumber(String mobileNumber);

    /**
     * Get all registered user details.
     *
     * @return List<User>    details of registered user details.
     */
    @Override
    List<User> findAll();
}
