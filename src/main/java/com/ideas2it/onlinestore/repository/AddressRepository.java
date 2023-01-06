/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideas2it.onlinestore.model.Address;

/**
 * Repository of Address
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}