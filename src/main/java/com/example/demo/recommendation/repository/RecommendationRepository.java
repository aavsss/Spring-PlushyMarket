package com.example.demo.recommendation.repository;

import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.recommendation.repository.models.HitCountInDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<HitCountInDB, Long> {

    @Query("SELECT p FROM PlushyInDB p, HitCountInDB h WHERE p.id = h.productId ORDER BY h.hitCount")
    List<PlushyInDB> getTopXRecommendations(
        @Param("x") Integer x
    );
}