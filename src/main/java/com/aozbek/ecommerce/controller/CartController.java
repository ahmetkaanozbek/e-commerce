package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.dto.CartRequestWrapper;
import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> addItemToCart(@RequestBody CartRequestWrapper cartRequestWrapper) {
        cartService.addItemToCart(cartRequestWrapper);
        return ResponseEntity.status(HttpStatus.CREATED).body("An item has been added to cart successfully.");
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem updatedCartItem) {
        CartItem newCartItem = cartService.updateCartItem(updatedCartItem);
        return ResponseEntity.status(HttpStatus.OK).body(newCartItem);
    }

}
