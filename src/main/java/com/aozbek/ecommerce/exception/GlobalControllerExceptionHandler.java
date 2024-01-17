package com.aozbek.ecommerce.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(CategoryNotExist.class)
    public ResponseEntity<String> handleCategoryNotExist(CategoryNotExist ex) {
        log.error("CategoryNotExist exception has been occurred. Message: "  + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductNotExist.class)
    public ResponseEntity<String> handleProductNotExist(ProductNotExist ex) {
        log.error("ProductNotExist exception has been occurred. Message: "  + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
