package com.example.demo.buy.service;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.crud.repository.models.PlushyInDB;

import java.util.List;

public interface BuyService {
    List<PlushyInDB> buyPlushyById(List<BuyRequestBody> plushiesToBuy);
}
