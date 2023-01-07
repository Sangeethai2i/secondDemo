/*
 * Copyright (c) 2022 - 2024 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.util.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.AddressDTO;
import com.ideas2it.onlinestore.dto.ProductDTO;
import com.ideas2it.onlinestore.dto.RoleDTO;
import com.ideas2it.onlinestore.dto.UserDTO;
import com.ideas2it.onlinestore.dto.WishlistDTO;
import com.ideas2it.onlinestore.model.Address;
import com.ideas2it.onlinestore.model.Product;
import com.ideas2it.onlinestore.model.Role;
import com.ideas2it.onlinestore.model.User;
import com.ideas2it.onlinestore.model.Wishlist;

/**
 * Mapper class of the user.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-21
 */
public class UserMapper {

    /**
     * Converts the user DTO object into user DAO objects
     *
     * @param userDTO   details of the user.
     * @return User     converted user details.
     */
    public static User convertUserDTOToDAO(UserDTO userDTO) {
        System.out.println(userDTO.getFirstName());
        User user = null;//new User();

        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setMobileNumber(userDTO.getMobileNumber());
        return user;
    }

    /**
     * Converts the user DAO object into user DTO object.
     *
     * @param user       details of the user DAO.
     * @return UserDTO   details of the user DTO.
     */
    public static UserDTO convertUserDAOToDTO(User user) {
        UserDTO userDTO = null;//new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNumber(user.getMobileNumber());
        return userDTO;
    }

    /**
     * Converts the address DTO object into address DAO object.
     *
     * @param addressDTO   details of the address DTO.
     * @return Address     details of the address DAO.
     */
    public Address convertAddressDTOToDAO(AddressDTO addressDTO) {
        Address address = null;//new Address();

        address.setId(addressDTO.getId());
        address.setDoorNumber(addressDTO.getDoorNumber());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPinCode(addressDTO.getPinCode());
        address.setType(addressDTO.getType());
        address.setLandmark(addressDTO.getLandmark());
        address.setUser(convertUserDTOToDAO(addressDTO.getUser()));
        return address;
    }

    /**
     * Converts the address DAO object into address DTO object.
     *
     * @param address         details of the address DAO.
     * @return AddressDTO     details of the address DTO.
     */
    public AddressDTO convertAddressDAOToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId(address.getId());
        addressDTO.setDoorNumber(address.getDoorNumber());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setPinCode(address.getPinCode());
        addressDTO.setType(address.getType());
        addressDTO.setLandmark(address.getLandmark());
        return addressDTO;
    }

    /**
     * Converts the role DTO object into role DAO object.
     *
     * @param roleDTO    details of the role DTO.
     * @return Role      details of the role DAO.
     */
    public Role convertRoleDTOToDAO(RoleDTO roleDTO) {
        Role role = null;//new Role();

        role.setId(roleDTO.getId());
        role.setType(roleDTO.getType());
        return role;
    }

    /**
     * Converts the role DAO object into role DTO object.
     *
     * @param role        details of the role DAO.
     * @return RoleDTO    details of the role DTO.
     */
    public RoleDTO convertRoleDAOToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId(role.getId());
        roleDTO.setType(role.getType());
        return roleDTO;
    }

    /**
     * Converts the wishlist DAO object into wishlist DTO object
     * also converts the product DAO object into product DTO object.
     *
     * @param wishlist        details of the wishlist DAO.
     * @return WishlistDTO    details of the wishlist DTO.
     */
    public WishlistDTO convertWishlistDAO(Wishlist wishlist) {
        WishlistDTO wishlistDTO = new WishlistDTO();

        wishlist.setId(wishlistDTO.getId());
        wishlistDTO.setName(wishlist.getName());

        if (!wishlist.getProducts().isEmpty()) {
            List<ProductDTO> products = new ArrayList<>();
            ProductMapper productMapper = new ProductMapper();

            for (Product product: wishlist.getProducts()) {
                products.add(productMapper.convertProductToProductDTO(product));
            }
            wishlistDTO.setProducts(products);
        }
        return wishlistDTO;
    }

    /**
     * Converts the wishlist DTO object into wishlist DAO object.
     *
     * @param wishlistDTO    details of the wishlist DAO.
     * @return Wishlist      details of the wishlist DTO.
     */
    public Wishlist convertWishlistDTO(WishlistDTO wishlistDTO) {
        Wishlist wishlist = null;//new Wishlist();

        wishlist.setId(wishlistDTO.getId());
        wishlist.setName(wishlistDTO.getName());
        return wishlist;
    }

    public User convertUserDTO(UserDTO userDTO) {
        System.out.println(userDTO.getFirstName());
        User user = convertUserDTOToDAO(userDTO);

        if (!(userDTO.getAddresses().isEmpty())) {
            List<Address> addresses = new ArrayList<>();
            for (AddressDTO addressDTO: userDTO.getAddresses()) {
                addresses.add(convertAddressDTOToDAO(addressDTO));
            }
            user.setAddresses(addresses);
        }

        if (!(userDTO.getRoles().isEmpty())) {
            List<Role> roles = new ArrayList<>();
            for (RoleDTO role: userDTO.getRoles()) {
                roles.add(convertRoleDTOToDAO(role));
            }
            user.setRoles(roles);
        }


        return user;
    }

    public UserDTO convertUserDAO(User user) {
        UserDTO userDTO = convertUserDAOToDTO(user);

        if (!(user.getAddresses().isEmpty())) {
            List<AddressDTO> addresses = new ArrayList<>();
            for (Address address: user.getAddresses()) {
                addresses.add(convertAddressDAOToDTO(address));
            }
            userDTO.setAddresses(addresses);
        }

        if (!(user.getRoles().isEmpty())) {
            List<RoleDTO> roles = new ArrayList<>();
            for (Role role: user.getRoles()) {
                roles.add(convertRoleDAOToDTO(role));
            }
            userDTO.setRoles(roles);
        }
        return userDTO;
    }
}
