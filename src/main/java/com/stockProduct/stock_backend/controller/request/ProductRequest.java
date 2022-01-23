package com.stockProduct.stock_backend.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;
    private MultipartFile image;
    private int price;
    private int stock;
}
