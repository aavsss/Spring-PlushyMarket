package com.example.demo.crud.service;

import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.PlushyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlushyServiceImpl implements PlushyService{

    private final PlushyRepository plushyRepository;

    @Autowired
    public PlushyServiceImpl(PlushyRepository plushyRepository) {
        this.plushyRepository = plushyRepository;
    }

    @Override
    public List<Plushy> getPlushies() {
        return plushyRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")
        );
    }

    @Override
    public Plushy getPlushyById(Long id) {
        Optional<Plushy> plushyOptional = plushyRepository.findById(id);
        return plushyOptional.orElse(null);
    }

    @Override
    public void addPlushy(Plushy plushy) {
        Optional<Plushy> plushyOptional = plushyRepository.findById(plushy.getId());
        if (plushyOptional.isPresent()) {
            throw new IllegalStateException("plushy already taken");
        }
        plushyRepository.save(plushy);
    }

    @Override
    public void deletePlushy(Long plushyId) {
        boolean exists = plushyRepository.existsById(plushyId);
        if (!exists) {
            throw new IllegalStateException(
                    "plushy with id " + plushyId + " does not exist."
            );
        }
        plushyRepository.deleteById(plushyId);
    }


}
