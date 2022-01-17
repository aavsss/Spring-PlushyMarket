package com.example.demo.crud.repository;

import com.example.demo.crud.model.Plushy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlushyRepository
        extends JpaRepository<Plushy, Long> {

    @Query("SELECT p FROM Plushy p JOIN p.plushiesInCart")
    List<Plushy> getPlushyInfoFromCart();
}
