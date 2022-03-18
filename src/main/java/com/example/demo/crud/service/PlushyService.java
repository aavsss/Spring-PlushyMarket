package com.example.demo.crud.service;

import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.model.UploadRequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface  PlushyService {
    List<Plushy> getPlushies();
    Plushy getPlushyById(Long id);
    void addPlushy(Plushy plushy);
    void deletePlushy(Long plushyId);
    void uploadPlushy(UploadRequestBody uploadRequestBody, MultipartFile multipartFile);
    String uploadPlushyImage(MultipartFile file);

}
