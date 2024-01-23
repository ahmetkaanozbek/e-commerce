package com.aozbek.ecommerce.exception;

public class UnauthorizedAction extends RuntimeException {
    public UnauthorizedAction() {
        super("Unauthorized action has been occurred.");
    }
}
