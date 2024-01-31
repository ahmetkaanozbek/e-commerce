package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.exception.CategoryNotExist;
import com.aozbek.ecommerce.model.Category;
import com.aozbek.ecommerce.repository.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableMethodSecurity
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category updateCategory(Category category, Long categoryId) {
        if (!(categoryRepository.existsById(categoryId))) {
            throw new CategoryNotExist();
        }
        Category updatedCategory = categoryRepository.getReferenceById(categoryId);
        updatedCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(updatedCategory);
    }

    /*
     To delete a category one must move or delete all the products belongs to
     that category to another category. Due to existence of foreign key and to
     protect data integrity.
    */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(Long categoryId) {
        if (!(categoryRepository.existsById(categoryId))) {
            throw new CategoryNotExist();
        }
        categoryRepository.deleteById(categoryId);
    }
}
