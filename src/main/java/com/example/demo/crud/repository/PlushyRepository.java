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

    @Query("SELECT p FROM PlushyInDB p WHERE p.ownerId=?1")
    Optional<List<PlushyInDB>> findAllByOwner(Long ownerId);
}
