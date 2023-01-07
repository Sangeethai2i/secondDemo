package com.ideas2it.onlinestore.util.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.CartProductDTO;
import com.ideas2it.onlinestore.model.CartProduct;
/**
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */
@Component
public class CartProductMapper {	
	
	private ProductMapper productMapper;
	
	@Autowired
	public CartProductMapper(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	/**
	 * 
	 * @param cartProduct
	 * @return
	 */
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
	
	/**
	 * 
	 * @param cartProductDTO
	 * @return
	 */
	public CartProduct convertCartProductDTOToEntity(CartProductDTO cartProductDTO) {
		CartProduct cartProduct = null;
		
		if (null != cartProductDTO) {
			cartProduct = new CartProduct();
			cartProduct.setId(cartProductDTO.getId());
			cartProduct.setQuantity(cartProductDTO.getQuantity());
			cartProduct.setProduct(productMapper.convertProductDTOToProduct(cartProductDTO.getProduct()));
		}
		return cartProduct;
	}
	
	/**
	 * 
	 * @param cartProducts
	 * @return
	 */
	public List<CartProductDTO> convertCartProductsToDTOs(List<CartProduct> cartProducts) {
		List<CartProductDTO> cartProductDTOs = new ArrayList<>();
		
		for (CartProduct cartProduct : cartProducts) {
			CartProductDTO cartProductDTO = convertCartProductEntityToDTO(cartProduct);
			cartProductDTOs.add(cartProductDTO);
		}
		return cartProductDTOs;
	}
	
	/**
	 * 
	 * @param cartProductDTOs
	 * @return
	 */
	public List<CartProduct> convertCartProductDTOsToCartProducts(List<CartProductDTO> cartProductDTOs) {
		List<CartProduct> cartProducts = new ArrayList<>();
		
		for (CartProductDTO cartProductDTO : cartProductDTOs) {
			CartProduct cartProduct = convertCartProductDTOToEntity(cartProductDTO);
			cartProducts.add(cartProduct);
		}
		return cartProducts;
	}

}
