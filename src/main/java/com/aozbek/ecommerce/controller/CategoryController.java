package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.model.Category;
import com.aozbek.ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categoryList = categoryService.getCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categoryList);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PatchMapping(value = "/update/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category,
                                                   @PathVariable Long categoryId) {
        Category updatedCategory = categoryService.updateCategory(category, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }

    @DeleteMapping(value = "/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body("A category has been deleted successfully.");
    }
}
