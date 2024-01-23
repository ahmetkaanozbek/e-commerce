package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.dto.CartRequestWrapper;
import com.aozbek.ecommerce.dto.GetUserCartDto;
import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.model.User;
import com.aozbek.ecommerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(value = "/get-cart")
    public ResponseEntity<List<GetUserCartDto>> getAllItems() {
        List<GetUserCartDto> allCartItems = cartService.getAllItems();
        return ResponseEntity.status(HttpStatus.OK).body(allCartItems);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> addItemToCart(@RequestBody CartRequestWrapper cartRequestWrapper) {
        cartService.addItemToCart(cartRequestWrapper);
        return ResponseEntity.status(HttpStatus.CREATED).body("An item has been added to cart successfully.");
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<String> updateCartItem(@RequestBody CartItem updatedCartItem) {
        cartService.updateCartItem(updatedCartItem);
        return ResponseEntity.status(HttpStatus.OK).body("An items quantity has been updated successfully.");
    }

    @DeleteMapping(value = "/remove")
    public ResponseEntity<String> removeCartItem(@RequestBody CartItem removedCartItem) {
        cartService.removeCartItem(removedCartItem);
        return ResponseEntity.status(HttpStatus.OK).body("An item has been deleted successfully.");
    }

    @DeleteMapping(value = "/clear")
    public ResponseEntity<String> clearAllCart(@RequestBody User usernameToClearCart) {
        cartService.clearAllCart(usernameToClearCart);
        return ResponseEntity.status(HttpStatus.OK).body("Cart has been cleared successfully.");
    }
}
