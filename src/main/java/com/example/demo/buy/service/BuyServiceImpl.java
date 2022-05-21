package com.example.demo.buy.service;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.crud.repository.models.PlushyInDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuyServiceImpl implements BuyService {

    private final PlushyRepository plushyRepository;
    private final CartRepository cartRepository;

    @Autowired
    public BuyServiceImpl(PlushyRepository plushyRepository, CartRepository cartRepository) {
        this.plushyRepository = plushyRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public List<PlushyInDB> buyPlushyById(List<BuyRequestBody> plushiesToBuy) {
        List<Long> ids = new ArrayList<>();
        for ( BuyRequestBody requestBody: plushiesToBuy) {
            ids.add(requestBody.getId());
        }
        List<PlushyInDB> plushies = plushyRepository.findAllById(ids);
        for(PlushyInDB plushyInDB : plushies) {
            for (BuyRequestBody buyRequestBody : plushiesToBuy) {
                if (plushyInDB.getId().equals(buyRequestBody.getId())) {
                    plushyInDB.setQuantity(plushyInDB.getQuantity() - buyRequestBody.getAmount());
                    plushyInDB.setQuantitySold(plushyInDB.getQuantitySold() + buyRequestBody.getAmount());
                }
            }
        }
        cartRepository.deleteAll();
        return plushies;
    }
}
