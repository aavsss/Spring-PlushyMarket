package com.example.demo.appUser.userContext.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellingAnalytics {
    private Integer active;
    private Integer totalQuantitiesSold;
    private Integer totalQuantitiesAvailable;
    private Integer totalMoneyEarned;
}
