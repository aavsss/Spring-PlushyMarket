package com.example.demo.cart.service;

import com.example.demo.cart.repository.models.CartInDB;
import com.example.demo.cart.repository.models.CartId;
import com.example.demo.cart.model.PlushyInCart;

import java.util.List;

public interface CartService {
    Long getNumInCart(Long userId);
    Long setItemInCart(CartInDB cartInDB);
    Long deleteItemFromCart(CartId cartId);
    List<PlushyInCart> getPlushiesInCart(Long userId);
}
