package com.example.demo.crud.service;

import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.model.UploadRequestBody;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.globalService.FileService.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.example.demo.globalService.FileService.FileServiceImpl.S3_BUCKET_NAME;
import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class PlushyServiceImpl implements PlushyService{

    private final PlushyRepository plushyRepository;
    private final FileService fileService;

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

    @Override
    public void uploadPlushy(String plushy, MultipartFile multipartFile) {
        UploadRequestBody plushyJson = getJsonfrom(plushy);
        String imageUrl = "";
        if (multipartFile != null && !multipartFile.isEmpty()) {
            imageUrl = uploadPlushyImage(multipartFile);
        }
        plushyRepository.save(
                new Plushy(
                        plushyJson.getName(),
                        Math.toIntExact(plushyJson.getPrice()),
                        plushyJson.getQuantity(),
                        plushyJson.getDescription(),
                        imageUrl
                )
        );
    }

    private UploadRequestBody getJsonfrom(String plushyStr) {
        UploadRequestBody plushy = new UploadRequestBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            plushy = objectMapper.readValue(plushyStr, UploadRequestBody.class);
        } catch (IOException err) {
            System.out.println("Error "+ err.toString());
        }
        System.out.println("/// json " + plushy);
        return plushy;
    }

    @Override
    public String uploadPlushyImage(MultipartFile file) {
        System.out.println("/// file " + file);
        if (file == null || file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File upload is not an image");
        }
        // get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        // save image in s3 and then save image in the database
        String path = String.format("%s/%s", S3_BUCKET_NAME, "plushy");
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
            return fileService.findByName("plushy/"+fileName);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
    }
}
