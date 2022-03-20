package com.example.demo.cart.controller;

import com.example.demo.cart.repository.models.CartInDB;
import com.example.demo.cart.repository.models.CartId;
import com.example.demo.cart.model.PlushyInCart;
import com.example.demo.cart.service.CartService;
import com.example.demo.helper.ObjectConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartInDBControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService mockCartService;
    private final ObjectConverter objectConverter = new ObjectConverter();

    @Test
    void getNumOfItemsInCart_returns200() throws Exception {
        Long numOfItems = 1L;
        when(mockCartService.getNumInCart(any())).thenReturn(numOfItems);
        mockMvc.perform(
                get("/api/v1/plushy/cart/{userId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void setItemInCart_returns200() throws Exception {
        CartInDB cartInDB = new CartInDB();
        Long numOfItemsInCart = 5L;
        when(mockCartService.setItemInCart(any())).thenReturn(numOfItemsInCart);
        mockMvc.perform(
                post("/api/v1/plushy/cart/set")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectConverter.asJsonString(cartInDB)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteItemFromCart_returns200() throws Exception {
        CartId cartId = new CartId();
        Long numOfItemsInCart = 5L;
        when(mockCartService.deleteItemFromCart(any())).thenReturn(numOfItemsInCart);
        mockMvc.perform(
                post("/api/v1/plushy/cart/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectConverter.asJsonString(cartId)))
                .andExpect(status().isOk());
    }

    @Test
    void getPlushiesInCart_returns200() throws Exception {
        List<PlushyInCart> plushyInCartList = List.of();
        when(mockCartService.getPlushiesInCart(any())).thenReturn(plushyInCartList);
        mockMvc.perform(
                get("/api/v1/plushy/cart/items"))
                .andExpect(status().isOk());
    }
}
