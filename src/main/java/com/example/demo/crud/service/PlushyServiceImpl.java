package com.example.demo.crud.service;

import com.example.demo.appUser.security.dependency.JWTPayload;
import com.example.demo.appUser.security.dependency.JWTUtils;
import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.globalService.fileService.FileService;
import com.example.demo.globalService.stringValidator.StringValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static com.example.demo.globalService.fileService.FileServiceImpl.S3_BUCKET_NAME;
import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class PlushyServiceImpl implements PlushyService {

    private final PlushyRepository plushyRepository;
    private final FileService fileService;
    private final JWTUtils jwtUtils;
    private final StringValidator stringValidator;

    @Override
    public List<PlushyInDB> getPlushies() {
        return plushyRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")
        );
    }

    @Override
    public PlushyInDB getPlushyById(Long id) {
        Optional<PlushyInDB> plushyOptional = plushyRepository.findById(id);
        return plushyOptional.orElseThrow();
    }

    @Override
    public List<PlushyInDB> getPlushiesByOwner(String jwtToken) {
        JWTPayload jwtPayload = jwtUtils.decodeJWTToken(jwtToken);
        Optional<List<PlushyInDB>> plushyInDBOptional = plushyRepository.findAllByOwner(jwtPayload.getEmail());
        return plushyInDBOptional.orElse(List.of());
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
                        imageUrl,
                        plushyJson.getOwnerEmail(),
                        0
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
            System.out.println("Error " + err);
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
        String path = String.format("%s/%s/%s", S3_BUCKET_NAME, "plushy", plushyId);
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
    public void updatePlushyImage(Long plushyId, MultipartFile file) {
        PlushyInDB plushyInDB = plushyRepository.findById(plushyId).orElseThrow();
        String imageUrl = uploadPlushyImage(plushyId, plushyInDB.getName(), file);
        plushyInDB.setImageUrl(imageUrl);
    }

    @Override
    @Transactional
    public void updatePlushy(Long plushyId, MultipartFile multipartFile, String plushy) {
        PlushyInDB plushyInDB = plushyRepository.findById(plushyId).orElseThrow();
        Plushy plushyJson = getJsonFrom(plushy);
        if (!stringValidator.isNullOrEmpty(plushyJson.getName()) && !plushyJson.getName().equals(plushyInDB.getName())) {
            plushyInDB.setName(plushyJson.getName());
        }
        if (!stringValidator.isNullOrEmpty(plushyJson.getDescription()) && !plushyJson.getDescription().equals(plushyInDB.getDescription())) {
            plushyInDB.setDescription(plushyJson.getDescription());
        }
        if (plushyJson.getPrice() != null && !plushyJson.getPrice().equals(plushyInDB.getPrice())) {
            plushyInDB.setPrice(plushyJson.getPrice());
        }
        if (plushyJson.getQuantity() != null && !plushyJson.getQuantity().equals(plushyInDB.getQuantity())) {
            plushyInDB.setQuantity(plushyJson.getQuantity());
        }
        if (multipartFile != null && !multipartFile.isEmpty()) {
            updatePlushyImage(plushyId, multipartFile);
        }
    }

}
