package com.stockProduct.stock_backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    String store(MultipartFile multipartFile);
}
