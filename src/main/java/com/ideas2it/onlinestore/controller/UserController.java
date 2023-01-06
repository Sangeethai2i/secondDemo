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

import com.ideas2it.onlinestore.util.customException.DataNotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.ideas2it.onlinestore.dto.AuthenticationRequest;
import com.ideas2it.onlinestore.dto.AuthenticationResponse;
import com.ideas2it.onlinestore.dto.UserDTO;
import com.ideas2it.onlinestore.service.UserService;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.constants.JwtUtil;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * if the given user details are not valid then throws
     * OnlineStoreException otherwise returns created object.
     *
     * @param userDTO                   details of the user.
     * @return UserDTO                  details of the user.
     */
    @PostMapping
    @ApiOperation(value = "Create User",
            notes = "This Api Create an account for the user using given details",
            response = UserDTO.class)
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        System.out.println(userDTO.getFirstName());
        return userService.createUser(userDTO);
    }

    /**
     * View the user details using the given user id
     * if the id is not exists throws OnlineStoreException
     * otherwise returns user details object.
     *
     * @param id                         id of the user.
     * @return UserDTO                   details of the user.
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "View User",
            notes = "This Api Gets the user details using given user id",
            response = UserDTO.class)
    public UserDTO getById(@ApiParam(name = "ID", value = "ID of the User")
                                               @PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    /**
     * Updates the user details using the given details
     * if the given user details are not valid throws OnlineStoreException
     * otherwise returns updated object.
     *
     * @param user                      details of the user.
     * @return UserDTO                  details of the user.
     */
    @PutMapping
    @ApiOperation(value = "Update user",
            notes = "This Api Updates the User details using given User details",
            response = UserDTO.class)
    public UserDTO updateUser(@Valid @RequestBody UserDTO user) {
        return userService.updateUser(user);
    }

    /**
     * Deletes the user details using given user id.
     * if the given user id is not exists throws OnlineStoreException
     * otherwise returns status message.
     *
     * @return ResponseEntity<String>  status message.
     */
    @DeleteMapping
    @ApiOperation(value = "Delete User Account",
            notes = "This Api Deletes the user account",
            response = String.class)
    public ResponseEntity<String> deleteUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser());
    }

    /**
     * Get all the registered user details.
     * if users details not exists throws
     * OnlineStoreException otherwise returns user details.
     *
     * @return List<UserDTO>             details of all registered user.
     */
    @GetMapping
    @ApiOperation(value = "Get All User",
            notes = "This Api Get all registered user details",
            response = UserDTO.class)
    public List<UserDTO> getAll() {
        return userService.getAllUser();
    }

    /**
     * Generates the user's log in token using the given request details
     * if the given credentials are invalid then throws OnlineStoreException
     * otherwise returns generated token.
     *
     * @param request                    log in details.
     * @return AuthenticationResponse    generated token.
     */
    @PostMapping("/login")
    @ApiOperation(value = "User Login",
            notes = "Allow user to access",
            response = AuthenticationResponse.class)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new DataNotFoundException(Constant.INVALID_CREDENTIALS);
        }
        return new AuthenticationResponse(JwtUtil.generateToken(request.getUsername()));
    }
}
