package com.ideas2it.onlinestore.util.mapper;

import com.ideas2it.onlinestore.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import com.ideas2it.onlinestore.dto.CartDTO;

public class CartMapper {


	CartProductMapper cartProductMapper;
	
	public CartDTO convertCartEntityToDTO(Cart cart) {
		CartDTO cartDTO = null;
		
		if (null != cart) {
			cartDTO = new CartDTO();
			cartDTO.setId(cart.getId());
			cartDTO.setCartTotal(cart.getCartTotal());
			//cartDTO.setCartProducts(cartProductMapper.convertCartProductsToDTOs(cart.getCartProducts()));
		}
		 return cartDTO;
	}
}
