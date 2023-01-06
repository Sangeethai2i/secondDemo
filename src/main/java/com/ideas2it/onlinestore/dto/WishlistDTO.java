/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * It represents the wishlist DTO.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-20
 */
public class WishlistDTO {
    private long id;
    @NotBlank(message = "name must be mentioned")
    private String name;
    @JsonIgnore
    private UserDTO user;
    @JsonIgnore
    private List<ProductDTO> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}

