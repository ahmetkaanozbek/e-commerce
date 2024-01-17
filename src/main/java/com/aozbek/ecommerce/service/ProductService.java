package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.dto.UpdatedProductDto;
import com.aozbek.ecommerce.exception.CategoryNotExist;
import com.aozbek.ecommerce.exception.ProductNotExist;
import com.aozbek.ecommerce.mapper.UpdatedProductMapper;
import com.aozbek.ecommerce.model.Product;
import com.aozbek.ecommerce.repository.CategoryRepository;
import com.aozbek.ecommerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UpdatedProductMapper updatedProductMapper;


    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          UpdatedProductMapper updatedProductMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.updatedProductMapper = updatedProductMapper;
    }

    public Product saveProduct(Product product) {
        Long categoryIdOfProduct = product.getCategory().getId();
        if (!(categoryRepository.existsById(categoryIdOfProduct))) {
            throw new CategoryNotExist();
        }
        return productRepository.save(product);
    }

    public Product updatedProduct(UpdatedProductDto updatedProductDto) {
        Long categoryIdOfProduct = updatedProductDto.getCategoryId();
        if (!(categoryRepository.existsById(categoryIdOfProduct))) {
            throw new CategoryNotExist();
        }
        if (!(productRepository.existsById(updatedProductDto.getId()))) {
            throw new ProductNotExist();
        }
        Product updatedProduct = updatedProductMapper.map(updatedProductDto);
        return productRepository.save(updatedProduct);
    }
}
