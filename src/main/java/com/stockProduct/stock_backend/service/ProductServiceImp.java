package com.stockProduct.stock_backend.service;

import com.stockProduct.stock_backend.controller.request.ProductRequest;
import com.stockProduct.stock_backend.exception.ProductNotFoundException;
import com.stockProduct.stock_backend.model.ProductModel;
import com.stockProduct.stock_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private StorageService storageService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductModel> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ProductModel getProductById(Long id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new ProductNotFoundException(id);
    }

    @Override
    public ProductModel createProduct(ProductRequest productRequest) {
        String fileName = storageService.store(productRequest.getImage());
        ProductModel data = new ProductModel();
        data.setName(productRequest.getName());
        data.setImage(fileName);
        data.setPrice(productRequest.getPrice());
        data.setStock(productRequest.getStock());
        return productRepository.save(data);
    }

    @Override
    public ProductModel updateProduct(ProductRequest productRequest, Long id) {
        Optional<ProductModel> product = productRepository.findById(id);
        String fileName = storageService.store(productRequest.getImage());
        if (product.isPresent()) {
            ProductModel existingProduct = product.get();
            if (fileName != null) {
                existingProduct.setImage(fileName);
            }
            existingProduct.setName(productRequest.getName());
            existingProduct.setPrice(productRequest.getPrice());
            existingProduct.setStock(productRequest.getStock());
            return productRepository.save(existingProduct);
        }
        throw new ProductNotFoundException(id);
    }

    @Override
    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
        }catch(Exception e) {
            throw new ProductNotFoundException(id);
        }
    }

    @Override
    public List<ProductModel> getProductByName(String name) {
        return productRepository.findByNameContaining(name);
    }
}
