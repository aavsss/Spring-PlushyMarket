package com.example.demo.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Revenue {
    private String month;
    private Integer earned;
}
