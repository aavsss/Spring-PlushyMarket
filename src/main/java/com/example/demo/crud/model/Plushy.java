package com.example.demo.crud.model;

import javax.persistence.*;

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
    private Long id;
    private String name;
    private Integer price;
    private Integer quantity;
    private String description;
    @Column(length = 1024)
    private String imageUrl;

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
