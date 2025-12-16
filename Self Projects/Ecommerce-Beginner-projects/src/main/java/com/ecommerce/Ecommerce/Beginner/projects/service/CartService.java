package com.ecommerce.Ecommerce.Beginner.projects.service;

import com.ecommerce.Ecommerce.Beginner.projects.dto.CartDto;
import com.ecommerce.Ecommerce.Beginner.projects.model.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllMyCart(String accessToken) throws Exception;
    Cart addItemsToCart(String accessToken, Long productId, CartDto cartDto) throws Exception;
    Cart updateItemsToCart(String accessToken, Long cartId, CartDto cartDto) throws Exception;
    String deleteItem(String accessToken, Long cartId) throws Exception;
}
