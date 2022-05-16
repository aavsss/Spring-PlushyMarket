package com.example.demo.appUser.userContext.service;

import com.example.demo.crud.repository.models.PlushyInDB;

import java.util.List;

public interface SellingAnalyticsService {
    Integer calculateTotalQuantitiesSold(List<PlushyInDB> plushyInDBList);
    Integer calculateTotalQuantitiesAvailable(List<PlushyInDB> plushyInDBList);
    Integer calculateMoneyEarned(List<PlushyInDB> plushyInDBList);
}
