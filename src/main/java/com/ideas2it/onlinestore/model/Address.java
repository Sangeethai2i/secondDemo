/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

/**
 * It is a simple JavaBean domain object representing an Address.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
@Entity
@Where(clause = "deleted = '0'")
public class Address extends BaseModel{
    @NotNull
    private String doorNumber;
    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private int pinCode;
    @NotNull
    private String state;
    @NotNull
    private String type;
    @NotNull
    private String landmark;

    @ManyToOne
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
