package com.example.demo.buy.controller;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.buy.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/plushy/buy")
public class BuyController {

    private final BuyService buyService;

    @Autowired
    public BuyController(BuyService buyService) {
        this.buyService = buyService;
    }

    @PostMapping
    public void buyPlushyById(
            @RequestBody List<BuyRequestBody> buyRequestBodies
    ) {
        buyService.buyPlushyById(buyRequestBodies);
    }
}
