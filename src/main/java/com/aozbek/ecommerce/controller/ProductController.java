package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.dto.UpdatedProductDto;
import com.aozbek.ecommerce.model.Product;
import com.aozbek.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value= "/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping(value = "/update/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody UpdatedProductDto updatedProductDto,
                                                 @PathVariable Long productId) {
        Product updatedProduct = productService.updatedProduct(updatedProductDto, productId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }
}
