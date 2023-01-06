package com.ideas2it.onlinestore.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.ideas2it.onlinestore.dto.CartDTO;
import com.ideas2it.onlinestore.model.Cart;

public class CartMapper {

	@Autowired
	CartProductMapper cartProductMapper;
	
	public CartDTO convertCartEntityToDTO(Cart cart) {
		CartDTO cartDTO = null;
		
		if (null != cart) {
			cartDTO = new CartDTO();
			cartDTO.setId(cart.getId());
			cartDTO.setCartTotal(cart.getCartTotal());
			cartDTO.setCartProducts(cartProductMapper.convertCartProductsToDTOs(cart.getCartProducts()));
		}
		 return cartDTO;
	}
}
