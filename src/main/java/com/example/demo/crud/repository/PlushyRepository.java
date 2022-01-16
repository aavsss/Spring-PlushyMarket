package com.example.demo.crud.repository;

import com.example.demo.crud.model.Plushy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlushyRepository
        extends JpaRepository<Plushy, Long> {
}
