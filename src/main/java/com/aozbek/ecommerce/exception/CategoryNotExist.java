package com.aozbek.ecommerce.exception;

public class CategoryNotExist extends RuntimeException {

    public CategoryNotExist() {
        super("This id doesn't belong to an existing Category.");
    }
}
