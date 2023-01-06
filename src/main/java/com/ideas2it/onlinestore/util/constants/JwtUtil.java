/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.util.constants;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

/**
 * This class implements the logic to create token 
 * 
 * @author arunkumar
 * @version 1.0
 */
@Component
public class JwtUtil {

	private static String key = "OnlineStore";

	/**
	 * This method get's the user name and generate the token
	 * 
	 * @param userName
	 * @return generated token
	 */
	public static String generateToken(String userName) {
		return Jwts.builder().setSubject(userName).setIssuer("arunkumar")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60)))
				.signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();
	}

	/**
	 * This method return given token based claims object
	 * 
	 * @param token
	 * @return claims object
	 */
	public static Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody();
	}
}
