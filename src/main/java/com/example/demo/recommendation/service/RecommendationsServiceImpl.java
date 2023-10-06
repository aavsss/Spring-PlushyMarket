package com.example.demo.recommendation.service;

import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.recommendation.repository.RecommendationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendationsServiceImpl implements RecommendationsService {

    private final RecommendationRepository recommendationRepository;

    @Override
    public List<PlushyInDB> getRecommendedPlushies() {
        return recommendationRepository.getTopXRecommendations(5);
    }
}
