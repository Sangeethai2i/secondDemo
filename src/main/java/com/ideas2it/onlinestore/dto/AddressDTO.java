/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ideas2it.onlinestore.util.constants.Constant;

/**
 * It represents the address DTO.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-20
 */
public class AddressDTO {

    private long id;
    @NotBlank(message = "Plot number must be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_DOOR_NUMBER, message = Constant.REGEX_FOR_INVALID_FORMAT)
    private String doorNumber;
    @NotBlank(message = "Street name must be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_TEXT, message = Constant.REGEX_FOR_INVALID_FORMAT)
    private String street;
    @NotBlank(message = "City Street name must be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_TEXT, message = Constant.REGEX_FOR_INVALID_FORMAT)
    private String city;
    @NotNull(message = "PinCode name must be mentioned")
    private int pinCode;
    @NotBlank(message = "State name must be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_TEXT, message = Constant.REGEX_FOR_INVALID_FORMAT)
    private String state;
    @NotBlank(message = "Address type name must be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_TEXT, message = Constant.REGEX_FOR_INVALID_FORMAT)
    private String type;
    @NotBlank(message = "Landmark name must be mentioned")
    @Pattern(regexp = Constant.REGEX_FOR_TEXT, message = Constant.REGEX_FOR_INVALID_FORMAT)
    private String landmark;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserDTO user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}