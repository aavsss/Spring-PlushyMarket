package com.example.demo.cart.controller;

import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartId;
import com.example.demo.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/plushy/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("{userId}")
    public Long getNumOfItemsInCart(
            @PathVariable("userId") Long userId
    ) {
        return cartService.getNumInCart(userId);
    }

    @PostMapping(path = "/set")
    public Long setItemInCart(
            @RequestBody Cart cart
    ) {
        return cartService.setItemInCart(cart);
    }

    @PostMapping(path = "/delete")
    public Long deleteItemFromCart(
            @RequestBody CartId cartId
    ) {
        return cartService.deleteItemFromCart(cartId);
    }

}
