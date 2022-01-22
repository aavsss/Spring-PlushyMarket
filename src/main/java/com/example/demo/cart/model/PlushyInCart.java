package com.example.demo.cart.model;

import javax.persistence.Column;

public class PlushyInCart {

    private Long id;
    private String name;
    private Integer price;
    private Integer userSelectedQuantity;
    private Integer maxQuantityInStock;
    private String description;
    private String imageUrl;

    public PlushyInCart() {
    }

    public PlushyInCart(
            Long id,
            String name,
            Integer price,
            Integer userSelectedQuantity,
            Integer maxQuantityInStock,
            String description,
            String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.userSelectedQuantity = userSelectedQuantity;
        this.maxQuantityInStock = maxQuantityInStock;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getUserSelectedQuantity() {
        return userSelectedQuantity;
    }

    public void setUserSelectedQuantity(Integer userSelectedQuantity) {
        this.userSelectedQuantity = userSelectedQuantity;
    }

    public Integer getMaxQuantityInStock() {
        return maxQuantityInStock;
    }

    public void setMaxQuantityInStock(Integer maxQuantityInStock) {
        this.maxQuantityInStock = maxQuantityInStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
