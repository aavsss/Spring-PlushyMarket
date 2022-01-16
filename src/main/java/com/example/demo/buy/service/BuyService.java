package com.example.demo.buy.service;

import com.example.demo.crud.model.Plushy;

public interface BuyService {
    Plushy buyPlushyById(Long plushyId, Integer amount);
}
