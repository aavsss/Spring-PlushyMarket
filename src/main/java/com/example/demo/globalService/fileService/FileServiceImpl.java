package com.example.demo.globalService.fileService;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    public final static String S3_BUCKET_NAME = "plushybucket";

    @Autowired
    private AmazonS3 amazonS3;

    private String generateUrl(String fileName, HttpMethod httpMethod) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        return amazonS3.generatePresignedUrl(
                S3_BUCKET_NAME, fileName, calendar.getTime(), httpMethod
        ).toString();
    }

    @Async
    @Override
    public String findByName(String fileName) {
        if (!amazonS3.doesObjectExist(S3_BUCKET_NAME, fileName))
            return "FILE DOES NOT EXIST";
        return generateUrl(fileName, HttpMethod.GET);
    }

    @Async
    @Override
    public String save(String fileName) {
        return generateUrl(fileName, HttpMethod.PUT);
    }

    @Async
    @Override
    public void upload(String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    @Async
    @Override
    public byte[] download(String path, String key) {
        try {
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream objectInputStream = object.getObjectContent();
            return IOUtils.toByteArray(objectInputStream);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }
}
