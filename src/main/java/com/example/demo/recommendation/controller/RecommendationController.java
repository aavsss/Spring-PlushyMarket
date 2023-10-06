package com.example.demo.recommendation.controller;

import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.recommendation.service.RecommendationsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/recommendations")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationsService recommendationsService;

    @GetMapping
    public List<PlushyInDB> getRecommendedPlushies() {
        return recommendationsService.getRecommendedPlushies();
    }
}
