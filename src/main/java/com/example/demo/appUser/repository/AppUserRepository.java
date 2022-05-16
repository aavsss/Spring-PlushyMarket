package com.example.demo.appUser.repository;

import com.example.demo.appUser.repository.models.AppUserInDB;
import com.example.demo.appUser.userContext.models.SellingAnalytics;
import com.example.demo.crud.repository.models.PlushyInDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUserInDB, String> {
    Optional<AppUserInDB> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUserInDB a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Query("SELECT p FROM PlushyInDB p WHERE p.ownerEmail = ?1")
    Optional<List<PlushyInDB>> getSellingAnalytics(String email);
}
