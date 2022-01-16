package com.example.demo.cart.service;

import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartId;

public interface CartService {
    Long getNumInCart(Long userId);
    Long setItemInCart(Cart cart);
    Long deleteItemFromCart(CartId cartId);
}
