/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ideas2it.onlinestore.dto.AddressDTO;
import com.ideas2it.onlinestore.model.Address;
import com.ideas2it.onlinestore.model.User;
import com.ideas2it.onlinestore.repository.AddressRepository;
import com.ideas2it.onlinestore.service.AddressService;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.configuration.JwtFilter;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * Service Implementation of Address.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
@Service
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;
    private Logger logger = LogManager.getLogger(AddressServiceImpl.class);

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addAddress(AddressDTO addressDTO) throws OnlineStoreException{
        User user = JwtFilter.getThreadLocal().get();
        ModelMapper modelMapper = new ModelMapper();
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);
        address = addressRepository.save(address);

        if (!(0 < address.getId())) {
            logger.error(Constant.ADDRESS_NOT_ADDED);
            throw new OnlineStoreException(Constant.ADDRESS_NOT_ADDED, HttpStatus.NOT_ACCEPTABLE);
        }
        logger.info(Constant.ADDRESS_ADDED_SUCCESSFULLY);
        return user.getFirstName() + Constant.ADDRESS_ADDED_SUCCESSFULLY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteAddress(long id) throws OnlineStoreException{
        User user = JwtFilter.getThreadLocal().get();
        List<Address> addresses = user.getAddresses();

        for (Address location : addresses) {
            if (id == location.getId()) {
                location.setDeleted(true);
                addressRepository.save(location);
                logger.info(Constant.ADDRESS_DELETED_SUCCESSFULLY);
                return user.getFirstName() + Constant.ADDRESS_DELETED_SUCCESSFULLY;
            }
        }
        logger.error(Constant.INVALID_DETAILS);
        throw new OnlineStoreException(Constant.INVALID_DETAILS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressDTO getAddressById(long id) throws OnlineStoreException {
        Address address = addressRepository.findById(id).orElse(null);
        
        if (null == address || address.isDeleted()) {
            logger.error(Constant.ADDRESS_NOT_FOUND);
            throw new OnlineStoreException(Constant.ADDRESS_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        ModelMapper modelMapper = new ModelMapper();
        logger.info(Constant.DETAILS_FETCHED_SUCCESSFULLY);
        return modelMapper.map(address, AddressDTO.class);
    }
}
