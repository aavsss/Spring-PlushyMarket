package com.example.demo.buy.repository;

import com.example.demo.crud.model.Plushy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyRepository
    extends JpaRepository<Plushy, Long> {
}
