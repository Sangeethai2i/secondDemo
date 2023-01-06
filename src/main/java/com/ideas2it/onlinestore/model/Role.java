/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.model;

import javax.persistence.Entity;

/**
 * It is a simple JavaBean domain object representing a Role.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
@Entity
public class Role extends BaseModel {
    private String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}