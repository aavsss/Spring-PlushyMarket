package com.example.demo.cart.service;

import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartId;
import com.example.demo.cart.model.PlushyInCart;
import com.example.demo.crud.model.Plushy;

import java.util.List;

public interface CartService {
    Long getNumInCart(Long userId);
    Long setItemInCart(Cart cart);
    Long deleteItemFromCart(CartId cartId);
    List<PlushyInCart> getPlushiesInCart(Long userId);
}
