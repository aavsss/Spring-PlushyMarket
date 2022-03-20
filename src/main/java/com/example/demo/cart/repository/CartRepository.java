package com.example.demo.cart.repository;

import com.example.demo.cart.model.PlushyInCart;
import com.example.demo.cart.repository.models.CartId;
import com.example.demo.cart.repository.models.CartInDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartInDB, CartId> {

    @Query("SELECT COUNT(c) FROM CartInDB c WHERE c.userId=:id")
    long getCountOfCartOfUser(
            @Param("id") Long userId
    );

    @Query("SELECT NEW com.example.demo.cart.model.PlushyInCart(p.id, p.name, p.price, c.quantity, p.quantity, p.description, p.imageUrl) FROM PlushyInDB p, CartInDB c WHERE p.id=c.productId")
    List<PlushyInCart> getPlushyInfoFromCartOf();

}
