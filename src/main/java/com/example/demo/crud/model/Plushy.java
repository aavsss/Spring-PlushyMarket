package com.example.demo.crud.model;

import com.example.demo.cart.model.Cart;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Plushy {
    @Id
    @SequenceGenerator(
            name = "plushy_sequence",
            sequenceName = "plushy_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plushy_sequence"
    )
    @Column(name = "plushy_id")
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
    private String description;
    @Column(length = 1024)
    private String imageUrl;
    @ManyToMany
    @JoinTable(
            name = "plushies_cart",
            joinColumns = @JoinColumn(name = "plushy_id"),
            inverseJoinColumns = {@JoinColumn(name = "user_id"), @JoinColumn(name = "product_id")}
    )
    Set<Cart> plushiesInCart;

    public Plushy() {
    }

    public Plushy(
            Long id,
            String name,
            Integer price,
            Integer quantity,
            String description,
            String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Plushy(
            String name,
            Integer price,
            Integer quantity,
            String description,
            String imageUrl) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Set<Cart> getLikedPlushies() {
        return plushiesInCart;
    }

    public void setLikedPlushies(Set<Cart> plushiesInCart) {
        this.plushiesInCart = plushiesInCart;
    }

    @Override
    public String toString() {
        return "Plushy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
