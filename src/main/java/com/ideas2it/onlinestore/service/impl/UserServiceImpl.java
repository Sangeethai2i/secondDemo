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

import com.ideas2it.onlinestore.service.CartService;
import com.ideas2it.onlinestore.util.configuration.JwtFilter;
import com.ideas2it.onlinestore.util.customException.DataNotFoundException;
import com.ideas2it.onlinestore.util.customException.RedundantDataException;
import com.ideas2it.onlinestore.util.customException.ResourcePersistenceException;
import com.ideas2it.onlinestore.util.mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.ideas2it.onlinestore.service.UserService;
import com.ideas2it.onlinestore.service.WishlistService;
import com.ideas2it.onlinestore.util.constants.Constant;

import javax.xml.crypto.Data;

/**
 * Service Implementation class for User.
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
    private Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserMapper userMapper = new UserMapper();

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
    public UserDTO createUser(UserDTO userDTO) {
        if (isEmailIdExists(userDTO.getEmail()) || isMobileNumberExists(userDTO.getMobileNumber())) {
            throw new RedundantDataException(Constant.EMAIL_ID_PHONE_NUMBER_EXISTS, HttpStatus.NOT_ACCEPTABLE);
        }
        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        System.out.println(userDTO.getFirstName());
        User createdUser = userRepository.save(userMapper.convertUserDTO(userDTO));
        UserDTO user = userMapper.convertUserDAO(createdUser);

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
            throw new ResourcePersistenceException(Constant.PROFILE_NOT_CREATED, HttpStatus.NOT_ACCEPTABLE);
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);

        if (null == user || user.isDeleted()) {
            throw new DataNotFoundException(Constant.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserDTO userDTO = userMapper.convertUserDAO(user);
        logger.info(Constant.DETAILS_FETCHED_SUCCESSFULLY);
        return userDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO updateUser(UserDTO user) {
        User existingUser = JwtFilter.getThreadLocal().get();

        if (null != existingUser) {
            user.setId(existingUser.getId());
            validByEmailId(user);
            validByPhoneNumber(user);
            user.setPassword(new BCryptPasswordEncoder().encode(existingUser.getPassword()));
            User userDAO = userMapper.convertUserDTOToDAO(user);
            userDAO.setRoles(existingUser.getRoles());
            userDAO.setAddresses(existingUser.getAddresses());
            userDAO = userRepository.save(userDAO);
            UserDTO userDTO = userMapper.convertUserDAO(userDAO);
            logger.info(Constant.UPDATED_SUCCESSFULLY);
            return userDTO;
        }
        throw new DataNotFoundException(Constant.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteUser() {
        User user = JwtFilter.getThreadLocal().get();
        user.setDeleted(true);
        userRepository.save(user);
        logger.info(Constant.DELETED_SUCCESSFULLY);
        return user.getFirstName() + Constant.DELETED_SUCCESSFULLY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        if (!users.isEmpty()) {

            for (User existingUser: users) {
                UserDTO user = userMapper.convertUserDAOToDTO(existingUser);
                usersDTO.add(user);
            }
            logger.info(Constant.DETAILS_FETCHED_SUCCESSFULLY);
        }
        return usersDTO;
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
     * @throws RedundantDataException   occur when email id already exit
     */
    private void validByEmailId(UserDTO user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (null != existingUser) {
            if (!(existingUser.getId() == (user.getId()))) {
                logger.error(Constant.EMAIL_ID_EXISTS);
                throw new RedundantDataException(Constant.EMAIL_ID_EXISTS, HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    /**
     * Validate phone number before update the user
     *
     * @param user                    details of the user.
     * @throws RedundantDataException   occur when email id already exit
     */
    private void validByPhoneNumber(UserDTO user) {
        User existingUser = userRepository.findByMobileNumber(user.getMobileNumber());

        if (null != existingUser) {
            if (!(user.getId() == (existingUser.getId())) ) {
                logger.error(Constant.MOBILE_NUMBER_EXISTS);
                throw new RedundantDataException(Constant.MOBILE_NUMBER_EXISTS, HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);

        if (null == user) {
            logger.error(Constant.USER_NOT_FOUND);
            throw new DataNotFoundException(Constant.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return user;
    }
}
