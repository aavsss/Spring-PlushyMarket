package com.example.demo.appUser.userContext.service;

import com.example.demo.crud.repository.models.PlushyInDB;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellingAnalyticsServiceImpl implements SellingAnalyticsService{
    @Override
    public Integer calculateTotalQuantitiesSold(List<PlushyInDB> plushyInDBList) {
        Integer totalQuantitiesSold = 0;
        for(PlushyInDB plushyInDB: plushyInDBList) {
            totalQuantitiesSold += plushyInDB.getQuantitySold();
        }
        return totalQuantitiesSold;
    }

    @Override
    public Integer calculateTotalQuantitiesAvailable(List<PlushyInDB> plushyInDBList) {
        Integer totalQuantitiesAvailable = 0;
        for (PlushyInDB plushyInDB: plushyInDBList) {
            totalQuantitiesAvailable += plushyInDB.getQuantity();
        }
        return totalQuantitiesAvailable;
    }

    @Override
    public Integer calculateMoneyEarned(List<PlushyInDB> plushyInDBList) {
        int totalMoneyEarned = 0;
        for (PlushyInDB plushyInDB: plushyInDBList) {
            totalMoneyEarned += plushyInDB.getQuantitySold() * plushyInDB.getPrice();
        }
        return totalMoneyEarned;
    }
}
