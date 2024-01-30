package com.aozbek.ecommerce.repository;

import com.aozbek.ecommerce.model.Category;
import com.aozbek.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Category category);
}
