package com.example.demo.buy.repository;

import com.example.demo.crud.repository.models.PlushyInDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyRepository
    extends JpaRepository<PlushyInDB, Long> {
}
