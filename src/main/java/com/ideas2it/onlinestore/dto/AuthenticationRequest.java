/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.dto;

/**
 * It represents the AuthenticationRequest.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-20
 */
public class AuthenticationRequest {


    private String email;

	private String password;
	
    public AuthenticationRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
