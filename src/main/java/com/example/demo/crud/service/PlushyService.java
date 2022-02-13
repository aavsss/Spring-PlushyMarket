package com.example.demo.crud.service;

import com.example.demo.crud.model.Plushy;

import java.util.List;

public interface  PlushyService {
    List<Plushy> getPlushies();
    Plushy getPlushyById(Long id);
    void addPlushy(Plushy plushy);
    void deletePlushy(Long plushyId);

}
