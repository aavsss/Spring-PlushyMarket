package com.example.demo.cart.repository;

import com.example.demo.cart.model.Cart;
import com.example.demo.cart.model.CartId;
import com.example.demo.cart.model.PlushyInCart;
import com.example.demo.crud.model.Plushy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CartRepository extends JpaRepository<Cart, CartId> {

    @Query("SELECT COUNT(c) FROM Cart c WHERE c.userId=:id")
    long getCountOfCartOfUser(
            @Param("id") Long userId
    );

    @Query("SELECT NEW com.example.demo.cart.model.PlushyInCart(p.id, p.name, p.price, c.quantity, p.quantity, p.description, p.imageUrl) FROM Plushy p, Cart c WHERE p.id=c.productId")
    List<PlushyInCart> getPlushyInfoFromCartOf();

}
