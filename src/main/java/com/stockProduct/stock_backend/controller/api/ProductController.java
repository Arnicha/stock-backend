package com.stockProduct.stock_backend.controller.api;

import com.stockProduct.stock_backend.controller.request.ProductRequest;
import com.stockProduct.stock_backend.exception.ValidationException;
import com.stockProduct.stock_backend.model.ProductModel;
import com.stockProduct.stock_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<ProductModel> getProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public ProductModel getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ProductModel addProduct(@Valid ProductRequest productRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(fieldError -> {
                throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }
        return productService.createProduct(productRequest);
    }

    @PutMapping("/{id}")
    public ProductModel updateProduct(@Valid ProductRequest productRequest, BindingResult bindingResult, @PathVariable long id) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(fieldError -> {
                throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }
        return productService.updateProduct(productRequest, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProductById(id);
    }

    @GetMapping(path = "/search", params = "name")
    public List<ProductModel> searchProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

}
