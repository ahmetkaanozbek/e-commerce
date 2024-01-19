package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.exception.CategoryNotExist;
import com.aozbek.ecommerce.model.Category;
import com.aozbek.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

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
    public void deleteCategory(Long categoryId) {
        if (!(categoryRepository.existsById(categoryId))) {
            throw new CategoryNotExist();
        }
        categoryRepository.deleteById(categoryId);
    }
}
