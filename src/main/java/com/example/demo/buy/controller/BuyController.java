package com.example.demo.buy.controller;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.buy.service.BuyService;
import com.example.demo.crud.model.Plushy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/plushy/buy")
public class BuyController {

    private final BuyService buyService;

    @Autowired
    public BuyController(BuyService buyService) {
        this.buyService = buyService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public Plushy buyPlushyById(
            @RequestBody BuyRequestBody buyRequestBody
    ) {
        return buyService.buyPlushyById(
                buyRequestBody.getId(),
                buyRequestBody.getAmount()
        );
    }
}
