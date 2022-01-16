package com.example.demo.cart.repository;

import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, CartId> {

    @Query("SELECT COUNT(c) FROM Cart c WHERE c.userId=:id")
    long getCountOfCartOfUser(
            @Param("id") Long userId
    );
}
