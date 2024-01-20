package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.dto.CartRequestWrapper;
import com.aozbek.ecommerce.exception.CartItemNotExist;
import com.aozbek.ecommerce.exception.ProductNotExist;
import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.repository.CartRepository;
import com.aozbek.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public void addItemToCart(CartRequestWrapper cartRequestWrapper) {
        // After adding authentication, I will get username from SecurityContextHolder.
        // So, for now, I won't check if that user exists or not.
        Long productId = cartRequestWrapper.getProduct().getId();

        if (!(productRepository.existsById(productId))) {
            throw new ProductNotExist();
        }
        if (cartRequestWrapper.getQuantity() > 0) {
            CartItem existingCartItem = cartRepository.findByUserAndProduct(
                    cartRequestWrapper.getUser(),
                    cartRequestWrapper.getProduct()
            );

            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequestWrapper.getQuantity());
                cartRepository.save(existingCartItem);
            } else {
                CartItem addedCartItem = new CartItem();
                addedCartItem.setUser(cartRequestWrapper.getUser());
                addedCartItem.setProduct(cartRequestWrapper.getProduct());
                addedCartItem.setQuantity(cartRequestWrapper.getQuantity());
            }
        }
    }


    public void updateCartItem(CartItem updatedCartItem) {
        // For now, in the request body I also need to add user. But after
        // authentication, cartItem will be enough for the request body.
        CartItem cartItem = cartRepository.findByUserAndId(
                updatedCartItem.getUser(),
                updatedCartItem.getId()
        );

        if (cartItem == null) {
            throw new CartItemNotExist();
        } else {
            cartItem.setQuantity(updatedCartItem.getQuantity());
            cartRepository.save(cartItem);
        }
    }
}
