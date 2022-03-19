package com.example.demo.crud.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UploadRequestBody {
    private String name;
    private Integer price;
    private Integer quantity;
    private String description;
    private String imageUrl;
}
