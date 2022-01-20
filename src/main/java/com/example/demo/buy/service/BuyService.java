package com.example.demo.buy.service;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.crud.model.Plushy;

import java.util.List;

public interface BuyService {
    List<Plushy> buyPlushyById(List<BuyRequestBody> plushiesToBuy);
}
