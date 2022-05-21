package com.example.demo.crud.controller;

import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.crud.service.PlushyService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/plushy")
@AllArgsConstructor
public class PlushyController {

    private final PlushyService plushyService;

    @GetMapping
    public List<PlushyInDB> getPlushies() {
        return plushyService.getPlushies();
    }

    @GetMapping(path = "{plushyId}")
    public PlushyInDB getPlushyById(
            @PathVariable("plushyId") Long id
    ) {
        return plushyService.getPlushyById(id);
    }

    @GetMapping(path = "/owner")
    public List<PlushyInDB> getPlushiesByOwner(
            @CookieValue(name = "token", defaultValue = "token") String jwtToken
    ) {
        return plushyService.getPlushiesByOwner(jwtToken);
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
            path = "/image/{plushyId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void updatePlushyImage(
            @PathVariable("plushyId") Long id,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        plushyService.updatePlushyImage(id, multipartFile);
    }

    @PutMapping(
            path = "/update/{plushyId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void updatePlushy(
            @PathVariable("plushyId") Long id,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            @RequestPart("plushy") String plushy
    ) {
        plushyService.updatePlushy(id, multipartFile, plushy);
    }
}
