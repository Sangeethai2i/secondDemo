/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * It is a simple JavaBean domain object representing a Role.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
public class RoleDTO  {
    private long id;
    @NotBlank(message = "Role must be mentioned")
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserDTO user;
}
