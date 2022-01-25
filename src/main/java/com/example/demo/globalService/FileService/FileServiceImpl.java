package com.example.demo.globalService.FileService;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

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
}
