package com.example.demo.cart.model;

import com.example.demo.crud.model.Plushy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@IdClass(CartId.class)
public class Cart {
    @Id
    private Long userId;
    @Id
    private Long productId;
    private Integer quantity;
    @ManyToMany(mappedBy = "plushiesInCart")
    Set<Plushy> plushies = new HashSet<>();

    public Cart() {}

    public Cart(Long userId, Long productId, Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Plushy> getPlushies() {
        return plushies;
    }

    public void setPlushies(Set<Plushy> plushies) {
        this.plushies = plushies;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
