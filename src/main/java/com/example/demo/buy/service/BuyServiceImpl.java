package com.example.demo.buy.service;

import com.example.demo.buy.repository.BuyRepository;
import com.example.demo.crud.model.Plushy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Plushy buyPlushyById(Long plushyId, Integer amount) {
        Optional<Plushy> plushyOptional = buyRepository.findById(plushyId);
        if (plushyOptional.isPresent()) {
            Plushy plushy = plushyOptional.get();
            if (plushy.getQuantity() >= amount) {
                plushy.setQuantity(plushy.getQuantity() - amount);
            }
            return plushy;
        }
        return null;
    }
}
