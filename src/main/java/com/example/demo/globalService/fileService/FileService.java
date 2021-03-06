package com.example.demo.globalService.fileService;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileService {
    String findByName(String fileName);
    String save(String fileName);
    void upload(
            String path, String fileName,
            Optional<Map<String, String>> optionalMetaData,
            InputStream inputStream
    );
    byte[] download(String path, String key);
}
