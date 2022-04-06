package com.example.demo.crud.service;

import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.models.PlushyInDB;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PlushyService {
    List<PlushyInDB> getPlushies();

    PlushyInDB getPlushyById(Long id);

    List<PlushyInDB> getPlushiesByOwner(String jwtToken);

    void addPlushy(PlushyInDB plushyInDB);

    void deletePlushy(Long plushyId);

    void uploadPlushy(String plushy, MultipartFile multipartFile);

    void updatePlushy(Long plushyId, Plushy plushy);

    void updatePlushyImage(String plushyId, MultipartFile file);
}
