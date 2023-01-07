package com.ideas2it.onlinestore.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ideas2it.onlinestore.dto.OrderDTO;
import com.ideas2it.onlinestore.model.Order;

@Component
public class OrderMapper {
	
	private UserMapper userMapper;
	private OrderProductMapper orderProductMapper;

	@Autowired
	public OrderMapper(UserMapper userMapper, OrderProductMapper orderProductMapper) {
		this.userMapper = userMapper;
		this.orderProductMapper = orderProductMapper;
	}

	public OrderDTO convertOrderEntityToOrderDTO(Order order) {
		OrderDTO orderDTO = null;
		
		if (null != order) {
			orderDTO = new OrderDTO();
			orderDTO.setId(order.getId());
			orderDTO.setDate(order.getDate());
			orderDTO.setStatus(order.getStatus());
			orderDTO.setAmount(order.getAmount());
			orderDTO.setAddress(userMapper.convertAddressDAOToDTO(order.getAddress()));
			orderDTO.setUser(userMapper.convertUserDAOToDTO(order.getUser()));
			orderDTO.setOrderProducts(orderProductMapper.convertOrderProductsToOrderProductDTOs(order.getOrderProducts()));
		}
		return orderDTO;
	}
	
	public Order convertOrderDTOToOrder(OrderDTO orderDTO) {
		Order order = null;
		
		if (null != orderDTO) {
			order = new Order();
			order.setId(orderDTO.getId());
			order.setAmount(orderDTO.getAmount());
			order.setAddress(userMapper.convertAddressDTOToDAO(orderDTO.getAddress()));
			order.setDate(orderDTO.getDate());
			order.setStatus(orderDTO.getStatus());
			orderDTO.getUser();
			orderDTO.getOrderProducts();
		}
		return order;
	}
}
