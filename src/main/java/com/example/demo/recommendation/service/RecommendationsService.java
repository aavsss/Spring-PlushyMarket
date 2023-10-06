package com.example.demo.recommendation.service;

import com.example.demo.crud.repository.models.PlushyInDB;

import java.util.List;

public interface RecommendationsService {
    List<PlushyInDB> getRecommendedPlushies();
}
