package com.ideas2it.onlinestore.util.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.OrderProductDTO;
import com.ideas2it.onlinestore.model.OrderProduct;

@Component
public class OrderProductMapper {

	private ProductMapper productMapper;
	
	public OrderProductMapper(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	public OrderProductDTO convertOrderProductToOrderProductDTO(OrderProduct orderProduct) {
		OrderProductDTO orderProductDTO = null;
		
		if (null != orderProduct) {
			orderProductDTO = new OrderProductDTO();
			orderProductDTO.setId(orderProduct.getId());
			orderProductDTO.setQuantity(orderProduct.getQuantity());
			orderProductDTO.setProduct(productMapper.convertProductToProductDTO(orderProduct.getProduct()));
		}
		return orderProductDTO;
	}
	
	public OrderProduct convertOrderProductDTOToOrderProduct(OrderProductDTO orderProductDTO) {
		OrderProduct orderProduct = null;
		
		if (null != orderProductDTO) {
			orderProduct = new OrderProduct();
			orderProduct.setId(orderProductDTO.getId());
			orderProduct.setQuantity(orderProductDTO.getQuantity());
			orderProduct.setProduct(productMapper.convertProductDTOToProduct(orderProductDTO.getProduct()));
		}
		return orderProduct;
	}
	
	public List<OrderProduct> convertOrderProductDTOsToOrderProducts(List<OrderProductDTO> orderProductDTOs) {
		List<OrderProduct> orderProducts = new ArrayList<>();
		
		for (OrderProductDTO orderProductDTO : orderProductDTOs) {
			OrderProduct orderProduct = convertOrderProductDTOToOrderProduct(orderProductDTO);
			orderProducts.add(orderProduct);
		}
		return orderProducts;
	}
	
	public List<OrderProductDTO> convertOrderProductsToOrderProductDTOs(List<OrderProduct> orderProducts) {
		List<OrderProductDTO> orderProductDTOs = new ArrayList<>();
		
		for (OrderProduct orderProduct : orderProducts) {
			OrderProductDTO orderProductDTO = convertOrderProductToOrderProductDTO(orderProduct);
			orderProductDTOs.add(orderProductDTO);
		}
		return orderProductDTOs;
	}
}
