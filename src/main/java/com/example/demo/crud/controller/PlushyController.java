package com.example.demo.crud.controller;

import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.model.UploadRequestBody;
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
    public List<Plushy> getPlushies(
            @CookieValue(name = "token", defaultValue = "token") String jwtToken
    ) {
        return plushyService.getPlushies();
    }

    @GetMapping(path = "{plushyId}")
    public Plushy getPlushyById(
            @PathVariable("plushyId") Long id
    ) {
        return plushyService.getPlushyById(id);
    }

//    @PostMapping
//    public void addPlushy(
//            @RequestBody UploadRequestBody uploadRequestBody,
//            HttpServletResponse response
//    ) {
//        System.out.println("/// ur " + uploadRequestBody.toString());
//        plushyService.uploadPlushy(uploadRequestBody);
//    }

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

    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadPlushy(
            @RequestBody UploadRequestBody uploadRequestBody,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        System.out.println("/// ur " + uploadRequestBody.toString());
        plushyService.uploadPlushy(uploadRequestBody, multipartFile);
    }

    @PostMapping(
            path="/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String uploadImage(
            @RequestParam(value = "file", required = false) MultipartFile multipartFile
    ) {
        return plushyService.uploadPlushyImage(multipartFile);
    }

}
