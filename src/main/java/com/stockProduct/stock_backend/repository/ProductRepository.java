package com.stockProduct.stock_backend.repository;

import com.stockProduct.stock_backend.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findByNameContaining(String name);
}
