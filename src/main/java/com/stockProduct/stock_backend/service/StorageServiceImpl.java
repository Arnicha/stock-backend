package com.stockProduct.stock_backend.service;

import com.stockProduct.stock_backend.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${app.upload.path:images}")
    private String path;

    private Path rootLocation;

    @Override
    public void init() {
        this.rootLocation = Paths.get(path);
        try {
            Files.createDirectories(rootLocation);
        }catch(IOException ex) {
            throw new StorageException("Could not init storage, " + ex.getMessage());
        }
    }

    @Override
    public String store(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new StorageException("Path outside current directory");
            }
            fileName = UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
            try(InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            }
        }catch (IOException ex) {
            throw new StorageException("Failed to storage file : " + fileName + ", " + ex.getMessage());
        }
    }
}
