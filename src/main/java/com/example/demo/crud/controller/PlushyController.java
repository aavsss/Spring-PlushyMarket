package com.example.demo.crud.controller;

import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.crud.service.PlushyService;
import com.example.demo.globalService.FileService.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping
    public List<PlushyInDB> getPlushies(
            @CookieValue(name = "token", defaultValue = "token") String jwtToken
    ) {
        return plushyService.getPlushies();
    }

    @GetMapping(path = "{plushyId}")
    public PlushyInDB getPlushyById(
            @PathVariable("plushyId") Long id
    ) {
        return plushyService.getPlushyById(id);
    }

    @DeleteMapping(path = "{plushyId}")
    public void deletePlushy(
            @PathVariable("plushyId") Long id
    ) {
        plushyService.deletePlushy(id);
    }

    @PostMapping(
            path = "/upload",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadPlushy(
            @RequestPart("plushy") String plushy,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        plushyService.uploadPlushy(plushy, multipartFile);
    }

    @PutMapping(
            path="/image",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void updatePlushyImage(
            @RequestPart("id") String id,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        plushyService.updatePlushyImage(id, multipartFile);
    }

    @PutMapping(path = "/update/{plushyId}")
    public void updatePlushy(
            @PathVariable("plushyId") Long id,
            @RequestBody Plushy plushy
    ) {
        plushyService.updatePlushy(id, plushy);
    }

}
