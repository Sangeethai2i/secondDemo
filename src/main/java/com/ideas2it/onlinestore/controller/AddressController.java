/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideas2it.onlinestore.dto.AddressDTO;
import com.ideas2it.onlinestore.dto.UserDTO;
import com.ideas2it.onlinestore.service.AddressService;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * Controller for Address.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Add the address to the user using given address details.
     * if the address details are not valid throws OnlineStoreException otherwise returns message.
     *
     * @param address                  details of the address.
     * @return ResponseEntity<String>  status message.
     * @throws OnlineStoreException    occur when user id or address details are not valid.
     */
    @PostMapping
    @ApiOperation(value = "Add Address",
            notes = "This Api add address details to the user",
            response = UserDTO.class)
    public ResponseEntity<String> add(@Valid @RequestBody AddressDTO address) throws OnlineStoreException {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.addAddress(address));
    }

    /**
     * Deletes the address from the user using given user and address id.
     * if the given user or address id is not valid throws OnlineStoreException
     * otherwise returns message.
     *
     * @param addressId                 id of the address.
     * @return ResponseEntity<String>   status message.
     * @throws OnlineStoreException     occur when user or address id is not found.
     */
    @DeleteMapping
    @ApiOperation(value = "Delete User Address",
            notes = "This Api Remove the address from the user account",
            response = UserDTO.class)
    public ResponseEntity<String> delete(
    		@ApiParam(name = "ID", value = "ID of the Address") 
			@RequestParam("ID") long addressId) throws OnlineStoreException {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.deleteAddress(addressId));
    }
}
