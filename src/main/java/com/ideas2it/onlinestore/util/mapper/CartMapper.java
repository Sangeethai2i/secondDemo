package com.ideas2it.onlinestore.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.CartDTO;
import com.ideas2it.onlinestore.model.Cart;
/**
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */
@Component
public class CartMapper {

	private CartProductMapper cartProductMapper;
	private UserMapper userMapper;
	
	@Autowired
	public CartMapper(CartProductMapper cartProductMapper,
			UserMapper userMapper) {
		this.cartProductMapper = cartProductMapper;
		this.userMapper = userMapper;
	}

	/**
	 * 
	 * @param cart
	 * @return
	 */
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
	
	public Cart convertCartDTOToEntity(CartDTO cartDTO) {
		Cart cart = null;
		
		if (null != cartDTO) {
			cart = new Cart();
			cart.setId(cartDTO.getId());
			cart.setUser(userMapper.convertUserDTOToDAO(cartDTO.getUser()));
			cart.setCartProducts(cartProductMapper.convertCartProductDTOsToCartProducts(cartDTO.getCartProducts()));
		}
		return cart;
	}
}
