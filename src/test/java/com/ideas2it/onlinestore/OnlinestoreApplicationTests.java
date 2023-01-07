package com.ideas2it.onlinestore;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ideas2it.onlinestore.dto.BrandDTO;
import com.ideas2it.onlinestore.dto.CartDTO;
import com.ideas2it.onlinestore.dto.CartProductDTO;
import com.ideas2it.onlinestore.dto.ProductDTO;
import com.ideas2it.onlinestore.dto.StockDTO;
import com.ideas2it.onlinestore.dto.UserDTO;
import com.ideas2it.onlinestore.model.User;
import com.ideas2it.onlinestore.service.AddressService;
import com.ideas2it.onlinestore.service.CartService;
import com.ideas2it.onlinestore.service.CategoryService;
import com.ideas2it.onlinestore.service.ProductService;
import com.ideas2it.onlinestore.service.StockService;
import com.ideas2it.onlinestore.service.UserService;
import com.ideas2it.onlinestore.util.configuration.JwtFilter;
import com.ideas2it.onlinestore.util.mapper.ProductMapper;
import com.ideas2it.onlinestore.util.mapper.UserMapper;

@SpringBootTest
class OnlinestoreApplicationTests {

	@MockBean
	StockService stockService;

	@MockBean
	ProductService productService;

	@MockBean
	UserService userService;

	@MockBean
	CartService cartService;
	
	@MockBean
	CategoryService categoryService;
	
	@MockBean
	AddressService addressService;

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	ProductMapper productMapper;

	@Test
	void checkQuantity() {
		assertEquals(44, stockService.getQuantity("redmi"));
	}

	@Test
	void addBrand() {
		BrandDTO brand = new BrandDTO();
		brand.setName("vivo");
		assertEquals("vivo", productService.addBrand(brand).getName());
	}

	@Test
	void getUserById() {
		assertEquals("arun@gmail.com", userService.getUserById(1l).getEmail());
	}

	@Test
	void createCart() {
		CartDTO cart = new CartDTO();
		CartProductDTO products = new CartProductDTO();
		products.setQuantity(10);
		products.setProduct(new ProductDTO());
		List<CartProductDTO> cartProducts = new ArrayList<>();
		cartProducts.add(products);
		cart.setUser(userMapper.convertUserDAO(JwtFilter.getThreadLocal().get()));
		cart.setCartProducts(cartProducts);
		assertEquals(cart, cartService.createCart(cart));
	}

	@Test
	void addStock() {
		assertEquals(12, stockService.getStockProductById(1l).getQuantity());
	}
	
	@Test
	void createUser() {
		User user = new User();
		 user.setFirstName("Gokul");
		 user.setMiddleName("Gopi");
		 user.setLastName("Varadan");
		 user.setEmail("gokul@gmail.com");
		 user.setPassword("gokul@123");
		 user.setMobileNumber("9960099938");
		 assertEquals("Gokul", userService.createUser(userMapper.convertUserDAO(user)));
	}
	
	@Test 
	void updateUser() {
		UserDTO user = userMapper.convertUserDAO(JwtFilter.getThreadLocal().get());
		user.setLastName("kumar");
		assertEquals(12, userService.updateUser(user));
	}
	
	@Test 
	void getStockProuctById() {
		assertEquals(1, stockService.getStockProductById(1).getId());
	}
	
	@Test 
	void deleteStock() {
		assertEquals(true, stockService.deleteStock(1));
	}
	
	@Test 
	void updateStock() {
		StockDTO stock = stockService.getStockProductById(1);
		stock.setQuantity(12);;
		assertEquals(true, stockService.updateStock(1, stock));
	}
	
	@Test
	void getAddressById() {
		assertEquals(1l, addressService.getAddressById(1).getId());
	}
}
