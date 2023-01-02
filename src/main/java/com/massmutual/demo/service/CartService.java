package com.massmutual.demo.service;

import java.util.List;

import com.massmutual.demo.entity.Cart;
import com.massmutual.demo.entity.Product;

public interface CartService {

	Cart addProductTocart(Integer cartId, Product product);

	Cart removeProductFromCart(Integer carrtId, Integer pid);

	Cart saveCart(Cart cart);

	Cart viewAllProducts(int cartId);

	Cart removeAllProducts(Cart cart);

	Cart updateProductQuantity(int cartId, int pid, int quantity);

}
