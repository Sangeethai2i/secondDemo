/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.util.configuration;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ideas2it.onlinestore.model.User;
import com.ideas2it.onlinestore.util.constants.JwtUtil;

import io.jsonwebtoken.Claims;

/**
 * This class customize the security configuration 
 * 
 * @author arunkumar
 * @version 1.0
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	UserDetailsService userDetailsService;
	
	public static ThreadLocal<User> threadLocal = new ThreadLocal<>();
	
	public static ThreadLocal<User> getThreadLocal() {
		return threadLocal;
	}
 
	public static void setThreadLocal(User user) {
		threadLocal.set(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (null != token)
		token = token.substring(7);	

		if (null != token) {
			Claims claims = JwtUtil.getClaims(token);
			String userName = claims.getSubject();
			SecurityContext securityContext = SecurityContextHolder.getContext();
			
			if (null != userName && null == securityContext.getAuthentication()
					&& !claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
				User user = (User) userDetailsService.loadUserByUsername(userName);
				setThreadLocal(user);
				UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(
						user.getUsername(), user.getPassword(), user.getAuthorities());
				userNamePassword.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				securityContext.setAuthentication(userNamePassword);
			}
		}
		filterChain.doFilter(request, response);
	}
}
