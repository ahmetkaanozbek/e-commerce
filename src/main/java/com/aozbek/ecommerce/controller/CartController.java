package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.dto.CartRequestWrapper;
import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping (value = "/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartRequestWrapper cartRequestWrapper) {
        CartItem addedItem = cartService.addItemToCart(cartRequestWrapper);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);
    }
}
