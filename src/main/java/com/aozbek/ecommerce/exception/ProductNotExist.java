package com.aozbek.ecommerce.exception;

public class ProductNotExist extends RuntimeException{
    public ProductNotExist() {
        super("This id doesn't belong to an existing Product.");
    }
}
