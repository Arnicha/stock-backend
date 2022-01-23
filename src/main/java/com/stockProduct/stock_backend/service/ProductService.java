package com.stockProduct.stock_backend.service;

import com.stockProduct.stock_backend.controller.request.ProductRequest;
import com.stockProduct.stock_backend.model.ProductModel;

import java.util.List;

public interface ProductService {
    List<ProductModel> getAllProduct();
    ProductModel getProductById(Long id);
    ProductModel createProduct(ProductRequest productRequest);
    ProductModel updateProduct(ProductRequest productRequest, Long id);
    void deleteProductById(Long id);
    List<ProductModel> getProductByName(String name);
}
