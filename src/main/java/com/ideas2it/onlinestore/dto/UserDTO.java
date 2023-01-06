/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ideas2it.onlinestore.model.Order;
import com.ideas2it.onlinestore.util.constants.Constant;

/**
 * It represents the user DTO.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-20
 */
public class UserDTO {
    private long id;

    @NotBlank(message = "First name should be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_TEXT, message = Constant.REGEX_FOR_INVALID_FORMAT)
    private String firstName;

    @NotBlank(message = "middle name should be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_TEXT, message = Constant.REGEX_FOR_INVALID_FORMAT)
    private String middleName;

    @NotBlank(message = "Last name should be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_TEXT, message = Constant.REGEX_FOR_INVALID_FORMAT)
    private String lastName;

    @NotBlank(message = "Email must be mentioned")
    @Email(regexp = Constant.REGEX_FOR_EMAIL_ID, message = "Invalid format e.g. vel@abc.com")
    private String email;

    @NotBlank(message = "Password must be mentioned")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Phone number must be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_PHONE_NUMBER, message = "Invalid format - Start with 6 - 9 e.g. 9**********")
    private String mobileNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<RoleDTO> roles;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<AddressDTO> addresses;
    @JsonIgnore
    private CartDTO cart;
    @JsonIgnore
    private WishlistDTO wishlist;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
    public CartDTO getCart() {
        return cart;
    }

    public WishlistDTO getWishlist() {
        return wishlist;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public void setWishlist(WishlistDTO wishlist) {
        this.wishlist = wishlist;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
