package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.exception.CategoryNotExist;
import com.aozbek.ecommerce.model.Product;
import com.aozbek.ecommerce.repository.CategoryRepository;
import com.aozbek.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product saveProduct(Product product) {
        Long categoryIdOfProduct = product.getCategory().getId();
        if (!(categoryRepository.existsById(categoryIdOfProduct))) {
            throw new CategoryNotExist();
        }
        return productRepository.save(product);
    }
}
