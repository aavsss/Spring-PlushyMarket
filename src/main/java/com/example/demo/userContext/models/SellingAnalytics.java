package com.example.demo.userContext.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SellingAnalytics {
    private Integer active;
    private Integer totalQuantitiesSold;
    private Integer totalQuantitiesAvailable;
    private Long totalMoneyEarned;
}
