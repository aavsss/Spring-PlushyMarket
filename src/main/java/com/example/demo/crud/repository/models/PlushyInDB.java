package com.example.demo.crud.repository.models;

import javax.persistence.*;

@Entity
@Table
public class PlushyInDB {
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
    private String ownerEmail;
    private Integer quantitySold;

    public PlushyInDB() {
    }

    public PlushyInDB(
            Long id,
            String name,
            Integer price,
            Integer quantity,
            String description,
            String imageUrl,
            String ownerEmail,
            Integer quantitySold
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ownerEmail = ownerEmail;
        this.quantitySold = quantitySold;
    }

    public PlushyInDB(
            String name,
            Integer price,
            Integer quantity,
            String description,
            String imageUrl,
            String ownerEmail,
            Integer quantitySold
    ) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ownerEmail = ownerEmail;
        this.quantitySold = quantitySold;
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

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
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
