/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.onlinestore.dto.CartDTO;
import com.ideas2it.onlinestore.dto.UserDTO;
import com.ideas2it.onlinestore.dto.WishlistDTO;
import com.ideas2it.onlinestore.model.Role;
import com.ideas2it.onlinestore.model.User;
import com.ideas2it.onlinestore.repository.UserRepository;
import com.ideas2it.onlinestore.service.AddressService;
import com.ideas2it.onlinestore.service.CartService;
import com.ideas2it.onlinestore.service.UserService;
import com.ideas2it.onlinestore.service.WishlistService;
import com.ideas2it.onlinestore.util.configuration.JwtFilter;
import com.ideas2it.onlinestore.util.constants.Constant;
import com.ideas2it.onlinestore.util.customException.OnlineStoreException;

/**
 * Service Implementation of User.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-17
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private WishlistService wishlistService;
    private AddressService addressService;
    private CartService cartService;
    Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, WishlistService wishlistService, AddressService addressService, CartService cartService) {
        this.userRepository = userRepository;
        this.wishlistService = wishlistService;
        this.addressService = addressService;
        this.cartService = cartService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) throws OnlineStoreException{
        if (isEmailIdExists(userDTO.getEmail()) || isMobileNumberExists(userDTO.getMobileNumber())) {
            throw new OnlineStoreException(Constant.EMAIL_ID_PHONE_NUMBER_EXISTS, HttpStatus.NOT_ACCEPTABLE);
        }
        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        User createdUser = userRepository.save(modelMapper.map(userDTO, User.class));
        UserDTO user = modelMapper.map(createdUser, UserDTO.class);

        if (0 < createdUser.getId()) {
            for (Role role: createdUser.getRoles()) {
                if (role.getType().equalsIgnoreCase("CUSTOMER")) {
                    WishlistDTO wishlist = new WishlistDTO();
                    CartDTO cartDTO = new CartDTO();
                    wishlist.setName(createdUser.getFirstName() + "Wishlist");
                    wishlist.setUser(user);
                    cartDTO.setUser(user);
                    wishlistService.createWishlist(wishlist);
                    cartService.createCart(cartDTO);
                    logger.info(Constant.PROFILE_CREATED);
                    break;
                }
            }
        } else {
            logger.error(Constant.PROFILE_NOT_CREATED);
            throw new OnlineStoreException(Constant.PROFILE_NOT_CREATED, HttpStatus.NOT_ACCEPTABLE);
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO getUserById(long id) throws OnlineStoreException {
        User user = userRepository.findById(id).orElse(null);

        if (null == user || user.isDeleted()) {
            logger.error(Constant.USER_NOT_FOUND);
            throw new OnlineStoreException(Constant.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setPassword(null);
        logger.info(Constant.DETAILS_FETCHED_SUCCESSFULLY);
        return userDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO updateUser(UserDTO user) throws OnlineStoreException {
        User existingUser = JwtFilter.getThreadLocal().get();

        if (null != existingUser) {
            user.setId(existingUser.getId());
            validByEmailId(user);
            validByPhoneNumber(user);
            user.setPassword(new BCryptPasswordEncoder().encode(existingUser.getPassword()));
            User userDAO = modelMapper.map(user, User.class);
            userDAO.setRoles(existingUser.getRoles());
            userDAO.setAddresses(existingUser.getAddresses());
            UserDTO userDTO = modelMapper.map(userRepository.save(userDAO), UserDTO.class);
            logger.info(Constant.UPDATED_SUCCESSFULLY);
            return userDTO;
        }
        logger.error(Constant.USER_NOT_FOUND);
        throw new OnlineStoreException(Constant.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteUser() throws OnlineStoreException{
        User user = JwtFilter.getThreadLocal().get();

        if (null == user) {
            logger.error(Constant.USER_NOT_FOUND);
            throw new OnlineStoreException(Constant.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setDeleted(true);
        userRepository.save(user);
        logger.info(Constant.DELETED_SUCCESSFULLY);
        return user.getFirstName() + Constant.DELETED_SUCCESSFULLY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getAllUser() throws OnlineStoreException {
        List<User> users = userRepository.findAll();

        if (!users.isEmpty()) {
            List<UserDTO> usersDTO = new ArrayList<>();

            for (User existingUser: users) {
                UserDTO user = modelMapper.map(existingUser, UserDTO.class);
                usersDTO.add(user);
            }
            logger.info(Constant.DETAILS_FETCHED_SUCCESSFULLY);
            return usersDTO;
        }
        logger.error(Constant.USER_NOT_EXISTS);
        throw new OnlineStoreException(Constant.USER_NOT_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Validates if the given email id is already exists or not.
     * if the given email id is already exists returns true otherwise false.
     *
     * @param userEmailId    Email id to validate
     * @return boolean       true or false
     */
    private boolean isEmailIdExists(String userEmailId) {
        List<String> userEmailIds = userRepository.findAll().stream().map(User::getEmail).toList();

        for (String emailId : userEmailIds) {
            if ( userEmailId.equals(emailId) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates if the given mobileNumber is already exists or not.
     * if the given mobileNumber is already exists returns true otherwise false.
     *
     * @param mobileNumber   mobileNumber to validate
     * @return boolean       true or false
     */
    private boolean isMobileNumberExists(String mobileNumber) {
        List<String> userMobileNumbers = userRepository.findAll().stream().map(User::getMobileNumber).toList();

        for (String number : userMobileNumbers) {
            if ( number.equals(mobileNumber) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate email id before update the user
     *
     * @param user                    details of the user.
     * @throws OnlineStoreException   occur when email id already exit
     */
    private void validByEmailId(UserDTO user) throws OnlineStoreException {
            User existingUser = userRepository.findByEmail(user.getEmail());

            if (null != existingUser) {
                if (!(existingUser.getId() == (user.getId()))) {
                    logger.error(Constant.EMAIL_ID_EXISTS);
                    throw new OnlineStoreException(Constant.EMAIL_ID_EXISTS, HttpStatus.NOT_ACCEPTABLE);
                }
            }
    }

    /**
     * Validate phone number before update the user
     *
     * @param user                    details of the user.
     * @throws OnlineStoreException   occur when email id already exit
     */
    private void validByPhoneNumber(UserDTO user) throws OnlineStoreException {
        User existingUser = userRepository.findByMobileNumber(user.getMobileNumber());

        if (null != existingUser) {
            if (!(user.getId() == (existingUser.getId())) ) {
                logger.error(Constant.MOBILE_NUMBER_EXISTS);
                throw new OnlineStoreException(Constant.MOBILE_NUMBER_EXISTS, HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (null == user) {
            logger.error(Constant.USER_NOT_FOUND);
            throw new OnlineStoreException(Constant.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return user;
    }
}
