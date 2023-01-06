package com.ideas2it.onlinestore.util.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.onlinestore.model.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;

import com.ideas2it.onlinestore.dto.CartProductDTO;

public class CartProductMapper {

	private ProductMapper productMapper;
	
	public CartProductDTO convertCartProductEntityToDTO(CartProduct cartProduct) {
		CartProductDTO cartProductDTO = null;
		
		if (null != cartProduct) {
			cartProductDTO = new CartProductDTO();
			cartProductDTO.setId(cartProduct.getId());
			cartProductDTO.setQuantity(cartProduct.getQuantity());
			cartProductDTO.setProduct(productMapper.convertProductToProductDTO(cartProduct.getProduct()));
		}
		return cartProductDTO;
	}
	
	public List<CartProductDTO> convertCartProductsToDTOs(List<CartProduct> cartProducts) {
		List<CartProductDTO> cartProductDTOs = new ArrayList<>();
		
		for (CartProduct cartProduct : cartProducts) {
			convertCartProductEntityToDTO(cartProduct);
		}
		return cartProductDTOs;
	}

}
