package com.example.demo.crud.service;

import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.globalService.FileService.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
    public List<PlushyInDB> getPlushies() {
        return plushyRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")
        );
    }

    @Override
    public PlushyInDB getPlushyById(Long id) {
        Optional<PlushyInDB> plushyOptional = plushyRepository.findById(id);
        return plushyOptional.orElse(null);
    }

    @Override
    public void addPlushy(PlushyInDB plushyInDB) {
        Optional<PlushyInDB> plushyOptional = plushyRepository.findById(plushyInDB.getId());
        if (plushyOptional.isPresent()) {
            throw new IllegalStateException("plushy already taken");
        }
        plushyRepository.save(plushyInDB);
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
    @Transactional
    public void uploadPlushy(String plushy, MultipartFile multipartFile) {
        Plushy plushyJson = getJsonFrom(plushy);
        String imageUrl = "";
        PlushyInDB plushyInDB = plushyRepository.save(
                new PlushyInDB(
                        plushyJson.getName(),
                        Math.toIntExact(plushyJson.getPrice()),
                        plushyJson.getQuantity(),
                        plushyJson.getDescription(),
                        imageUrl
                )
        );
        if (multipartFile != null && !multipartFile.isEmpty()) {
            imageUrl = uploadPlushyImage(plushyInDB.getId(), plushyInDB.getName(), multipartFile);
            plushyInDB.setImageUrl(imageUrl);
        }
    }

    private Plushy getJsonFrom(String plushyStr) {
        Plushy plushy = new Plushy();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            plushy = objectMapper.readValue(plushyStr, Plushy.class);
        } catch (IOException err) {
            System.out.println("Error "+ err.toString());
        }
        System.out.println("/// json " + plushy);
        return plushy;
    }

    public String uploadPlushyImage(Long plushyId, String fileName, MultipartFile file) {
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
        String path = String.format("%s/%s/%s", S3_BUCKET_NAME, "plushy",plushyId);
        fileName = String.format("%s.%s", fileName, "jpeg");
        try {
            fileService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
            return fileService.findByName(String.format("plushy/%s/%s", plushyId, fileName));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
    }

    @Override
    @Transactional
    public void updatePlushyImage(String plushyId, MultipartFile file) {
        PlushyInDB plushyInDB = plushyRepository.findById(Long.valueOf(plushyId)).orElseThrow();
        String imageUrl = uploadPlushyImage(Long.valueOf(plushyId), plushyInDB.getName(), file);
        plushyInDB.setImageUrl(imageUrl);
    }

    @Override
    @Transactional
    public void updatePlushy(Long plushyId, Plushy plushy) {
        PlushyInDB plushyInDB = plushyRepository.findById(plushyId).orElseThrow();

        if (plushy.getName() != null) {
            plushyInDB.setName(plushy.getName());
        }
        if (plushy.getDescription() != null) {
            plushyInDB.setDescription(plushy.getDescription());
        }
        if (plushy.getPrice() != null) {
            plushyInDB.setPrice(plushy.getPrice());
        }
        if (plushy.getQuantity() != null) {
            plushyInDB.setQuantity(plushy.getQuantity());
        }
    }
}
