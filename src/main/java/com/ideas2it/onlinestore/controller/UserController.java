/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.controller;

import java.util.List;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.onlinestore.dto.AuthenticationRequest;
import com.ideas2it.onlinestore.dto.AuthenticationResponse;
import com.ideas2it.onlinestore.dto.UserDTO;
import com.ideas2it.onlinestore.service.UserService;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.constants.JwtUtil;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * Controller for User
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Creates the user account using the given user details
     * if the given details are not valid then throws OnlineStoreException
     * otherwise returns created object.
     *
     * @param user                        details of the user.
     * @return ResponseEntity<UserDTO>    details of the user.
     * @throws OnlineStoreException       occur when user details or role is not matched.
     */
    @PostMapping
    @ApiOperation(value = "Create User",
            notes = "This Api Create an account for the user using given details",
            response = UserDTO.class)
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) throws OnlineStoreException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    /**
     * View the user details using the given user id
     * if the id is not exists throws OnlineStoreException otherwise returns user object.
     *
     * @param id                           id of the user.
     * @return ResponseEntity<UserDTO>     details of the user.
     * @throws OnlineStoreException        occur when given id not found.
     */
    @GetMapping
    @ApiOperation(value = "View User",
            notes = "This Api Gets the user details using given user id",
            response = UserDTO.class)
    public ResponseEntity<UserDTO> getById(@ApiParam(name = "ID", value = "ID of the User")
                                               @RequestParam("ID") long id) throws OnlineStoreException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    /**
     * Updates the user details using the given details
     * if the given user details are not valid throws OnlineStoreException
     * otherwise returns updated object.
     *
     * @param user                       details of the user.
     * @return ResponseEntity<UserDTO>   status message.
     * @throws OnlineStoreException      occur when given detail is not valid.
     */
    @PutMapping
    @ApiOperation(value = "Update user",
            notes = "This Api Updates the User details using given User details",
            response = UserDTO.class)
    public ResponseEntity<UserDTO> updateDetails(@Valid @RequestBody UserDTO user) throws OnlineStoreException{
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(user));
    }

    /**
     * Deletes the user details using given user id.
     * if the given id is not exists throws OnlineStoreException
     * otherwise returns status message.
     *
     * @return ResponseEntity<String>  status message.
     * @throws OnlineStoreException    occur when given id is not found.
     */
    @DeleteMapping
    @ApiOperation(value = "Delete User Account",
            notes = "This Api Deletes the user account",
            response = String.class)
    public ResponseEntity<String> deleteDetails() throws OnlineStoreException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser());
    }

    /**
     * Get all the registered user details.
     * if users not exists throws OnlineStoreException otherwise returns user details.
     *
     * @return ResponseEntity<List<UserDTO>>      details of all registered user.
     * @throws OnlineStoreException               occur when no user exists.
     */
    @GetMapping("/users")
    @ApiOperation(value = "Get All User",
            notes = "This Api Get all registered user details",
            response = UserDTO.class)
    public ResponseEntity<List<UserDTO>> getAll() throws OnlineStoreException{
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    /**
     * Generates the user's log in token using the given request details
     * if the given credentials are invalid then throws OnlineStoreException
     * otherwise returns generated token.
     *
     * @param request                    log in details.
     * @return AuthenticationResponse    generated token.
     * @throws OnlineStoreException      occur when given credentials are invalid.
     */
    @PostMapping("/login")
    @ApiOperation(value = "User Login",
            notes = "Allow user to access",
            response = AuthenticationResponse.class)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) throws OnlineStoreException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new OnlineStoreException(Constant.INVALID_CREDENTIALS);
        }
        return new AuthenticationResponse(JwtUtil.generateToken(request.getUsername()));
    }
}
