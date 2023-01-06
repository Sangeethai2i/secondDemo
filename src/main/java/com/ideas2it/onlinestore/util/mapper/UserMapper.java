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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.AddressDTO;
import com.ideas2it.onlinestore.dto.CartDTO;
import com.ideas2it.onlinestore.dto.CartProductDTO;
import com.ideas2it.onlinestore.dto.RoleDTO;
import com.ideas2it.onlinestore.dto.UserDTO;
import com.ideas2it.onlinestore.dto.WishlistDTO;
import com.ideas2it.onlinestore.model.Address;
import com.ideas2it.onlinestore.model.Cart;
import com.ideas2it.onlinestore.model.CartProduct;
import com.ideas2it.onlinestore.model.Role;
import com.ideas2it.onlinestore.model.User;
import com.ideas2it.onlinestore.model.Wishlist;
import com.ideas2it.onlinestore.service.ProductService;

/**
 * Mapper class of the user.
 *
 * @author - Gokul V
 * @version - 1.0
 * @since - 2022-12-21
 */
@Component
public class UserMapper {
    private long id = 0;
    
    @Autowired
    @Lazy
    private ProductService productService;

    public User convertUserDTOToDAO(UserDTO userDTO) {
        User user = new User();

        if (id < userDTO.getId()) {
            user.setId(userDTO.getId());
        }
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setMobileNumber(userDTO.getMobileNumber());
        return user;
    }

    public UserDTO convertUserDAOToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        if (id < user.getId()) {
            userDTO.setId(user.getId());
        }
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNumber(user.getMobileNumber());
        return userDTO;
    }

    public Address convertAddressDTOToDAO(AddressDTO addressDTO) {
        Address address = new Address();

        if (id < addressDTO.getId()) {
            address.setId(addressDTO.getId());
        }
        address.setDoorNumber(addressDTO.getDoorNumber());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPinCode(addressDTO.getPinCode());
        address.setType(addressDTO.getType());
        address.setLandmark(addressDTO.getLandmark());
        return address;
    }

    public AddressDTO convertAddressDAOToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        if (id < address.getId()) {
            addressDTO.setId(address.getId());
        }
        addressDTO.setDoorNumber(address.getDoorNumber());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setPinCode(address.getPinCode());
        addressDTO.setType(address.getType());
        addressDTO.setLandmark(address.getLandmark());
        return addressDTO;
    }

    public Role convertRoleDTOToDAO(RoleDTO roleDTO) {
        Role role = new Role();

        if (id < roleDTO.getId()) {
            role.setId(roleDTO.getId());
        }
        role.setType(roleDTO.getType());
        return role;
    }

    public RoleDTO convertRoleDAOToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();

        if (id < role.getId()) {
            roleDTO.setId(role.getId());
        }
        roleDTO.setType(role.getType());
        return roleDTO;
    }

    public User convertUserDTO(UserDTO userDTO) {
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
    
    public CartDTO convertCartDAO(Cart cart) {
    	CartDTO cartDTO = new CartDTO();
    	List<CartProduct> cartProducts = cart.getCartProducts();
    	List<CartProductDTO> cartProductDTOs = new ArrayList<>();
    	cartDTO.setCartTotal(cart.getCartTotal());
    	
    	for (CartProduct cartProduct : cartProducts) {
			CartProductDTO cartProductDTO = new CartProductDTO();
			cartProductDTO.setQuantity(cartProduct.getQuantity());
			cartProductDTO.setProduct(this.productService.getById(cartProduct.getProduct().getId()));
			cartProductDTOs.add(cartProductDTO);
		}    	
    	cartDTO.setCartProducts(cartProductDTOs);
    	return cartDTO;
    }

    public WishlistDTO convertWishlistDAO(Wishlist wishlist) {
        WishlistDTO wishlistDTO = new WishlistDTO();

        if (id < wishlist.getId()) {
            wishlistDTO.setId(wishlist.getId());
        }
        wishlistDTO.setName(wishlist.getName());
        return wishlistDTO;
    }

    public Wishlist convertWishlistDTO(WishlistDTO wishlistDTO) {
        Wishlist wishlist = new Wishlist();

        if (id < wishlistDTO.getId()) {
            wishlist.setId(wishlistDTO.getId());
        }
        wishlist.setName(wishlistDTO.getName());
        return wishlist;
    }
}
