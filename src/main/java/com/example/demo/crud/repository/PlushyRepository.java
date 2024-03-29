package com.example.demo.crud.repository;

import com.example.demo.crud.repository.models.PlushyInDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlushyRepository
        extends JpaRepository<PlushyInDB, Long> {

    @Query("SELECT p FROM PlushyInDB p WHERE p.ownerEmail=?1")
    Optional<List<PlushyInDB>> findAllByOwner(String ownerEmail);

    @Query("SELECT p FROM PlushyInDB p WHERE p.id IS NOT ?1")
    Optional<List<PlushyInDB>> findSimilarPlushies(Long id);
}
