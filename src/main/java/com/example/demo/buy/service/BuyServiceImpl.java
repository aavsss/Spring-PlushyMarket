package com.example.demo.buy.service;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.buy.repository.BuyRepository;
import com.example.demo.crud.model.Plushy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuyServiceImpl implements BuyService {

    private final BuyRepository buyRepository;

    @Autowired
    public BuyServiceImpl(BuyRepository buyRepository) {
        this.buyRepository = buyRepository;
    }

    @Override
    @Transactional
    public List<Plushy> buyPlushyById(List<BuyRequestBody> plushiesToBuy) {
        List<Long> ids = new ArrayList<>();
        for ( BuyRequestBody requestBody: plushiesToBuy) {
            ids.add(requestBody.getId());
        }
        List<Plushy> plushies = buyRepository.findAllById(ids);
        for(Plushy plushy : plushies) {
            for (BuyRequestBody buyRequestBody : plushiesToBuy) {
                if (plushy.getId().equals(buyRequestBody.getId())) {
                    plushy.setQuantity(plushy.getQuantity() - buyRequestBody.getAmount());
                }
            }
        }
        return plushies;
    }
}
