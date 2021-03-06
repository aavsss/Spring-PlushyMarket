package com.example.demo.crud.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Plushy {
    private String name;
    private Integer price;
    private Integer quantity;
    private String description;
    private String imageUrl;
    private String ownerEmail;
}
