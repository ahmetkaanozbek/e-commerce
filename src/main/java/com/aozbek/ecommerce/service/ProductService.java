package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.dto.UpdatedProductDto;
import com.aozbek.ecommerce.exception.CategoryNotExist;
import com.aozbek.ecommerce.exception.ProductNotExist;
import com.aozbek.ecommerce.mapper.UpdatedProductMapper;
import com.aozbek.ecommerce.model.Product;
import com.aozbek.ecommerce.repository.CategoryRepository;
import com.aozbek.ecommerce.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

@Service
@EnableMethodSecurity
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product saveProduct(Product product) {
        Long categoryIdOfProduct = product.getCategory().getId();
        if (!(categoryRepository.existsById(categoryIdOfProduct))) {
            throw new CategoryNotExist();
        }
        return productRepository.save(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product updateProduct(UpdatedProductDto updatedProductDto, Long productId) {
        Long categoryIdOfProduct = updatedProductDto.getCategoryId();
        if (!(categoryRepository.existsById(categoryIdOfProduct))) {
            throw new CategoryNotExist();
        }
        if (!(productRepository.existsById(productId))) {
            throw new ProductNotExist();
        }
        Product updatedProduct = updatedProductMapper.map(updatedProductDto);
        updatedProduct.setId(productId);
        return productRepository.save(updatedProduct);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProduct(Long productId) {
        if (!(productRepository.existsById(productId))) {
            throw new ProductNotExist();
        }
        productRepository.deleteById(productId);
    }
}
