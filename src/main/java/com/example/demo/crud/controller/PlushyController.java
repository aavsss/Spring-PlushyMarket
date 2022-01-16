package com.example.demo.crud.controller;

import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.service.PlushyService;
import com.example.demo.globalService.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/plushy")
public class PlushyController {

    private final PlushyService plushyService;
    private final FileServiceImpl fileService;

    @Autowired
    public PlushyController(
            PlushyService plushyService,
            FileServiceImpl fileService
    ){
        this.plushyService = plushyService;
        this.fileService = fileService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Plushy> getPlushies() {
        return plushyService.getPlushies();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "{plushyId}")
    public Plushy getPlushyById(
            @PathVariable("plushyId") Long id
    ) {
        return plushyService.getPlushyById(id);
    }

    @PostMapping
    public void addPlushy(
            @RequestBody Plushy plushy
    ) {
        plushyService.addPlushy(plushy);
    }

    @DeleteMapping(path = "{plushyId}")
    public void deletePlushy(
            @PathVariable("plushyId") Long id
    ) {
        plushyService.deletePlushy(id);
    }

    @GetMapping(path = "/pic")
    public String findByName() {
        return fileService.findByName("plushy/naruto.png");
    }

    // TODO put mapping

}
