package com.aozbek.ecommerce.exception;

public class CartItemNotExist extends RuntimeException {

    public CartItemNotExist() {
        super("This cart item doesn't exist.");
    }
}
