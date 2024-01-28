package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.dto.CartRequestWrapper;
import com.aozbek.ecommerce.dto.GetUserCartDto;
import com.aozbek.ecommerce.exception.CartItemNotExist;
import com.aozbek.ecommerce.exception.ProductNotExist;
import com.aozbek.ecommerce.exception.UnauthorizedAction;
import com.aozbek.ecommerce.mapper.GetUserCartMapper;
import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.model.User;
import com.aozbek.ecommerce.repository.CartRepository;
import com.aozbek.ecommerce.repository.ProductRepository;
import com.aozbek.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final GetUserCartMapper getUserCartMapper;
    private final AuthService authService;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository,
                       GetUserCartMapper getUserCartMapper,
                       AuthService authService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.getUserCartMapper = getUserCartMapper;
        this.authService = authService;
    }

    public List<GetUserCartDto> getAllItems() {
        User currentUser = userRepository.getUserByUsername(
                authService.getCurrentUser().getUsername()
        );
        List<CartItem> allItems = cartRepository.getAllByUser(currentUser);
        return getUserCartMapper.map(allItems);
    }

    public void addItemToCart(CartRequestWrapper cartRequestWrapper) {
        Long productId = cartRequestWrapper.getProduct().getId();
        User currentUser = authService.getCurrentUser();

        if (!(productRepository.existsById(productId))) {
            throw new ProductNotExist();
        }
        if (cartRequestWrapper.getQuantity() > 0) {
            CartItem existingCartItem = cartRepository.findByUserAndProduct(
                    currentUser,
                    cartRequestWrapper.getProduct()
            );

            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequestWrapper.getQuantity());
                cartRepository.save(existingCartItem);
            } else {
                CartItem addedCartItem = new CartItem();
                addedCartItem.setUser(currentUser);
                addedCartItem.setProduct(cartRequestWrapper.getProduct());
                addedCartItem.setQuantity(cartRequestWrapper.getQuantity());
                cartRepository.save(addedCartItem);
            }
        }
    }

    public void updateCartItem(CartItem updatedCartItem) {
        User currentUser = authService.getCurrentUser();
        CartItem cartItem = cartRepository.findByUserAndId(
                currentUser,
                updatedCartItem.getId()
        );

        if (cartItem == null) {
            throw new CartItemNotExist();
        } else {
            cartItem.setQuantity(updatedCartItem.getQuantity());
            cartRepository.save(cartItem);
        }
    }

    public void removeCartItem(CartItem removedCartItem) {
        CartItem cartItem = cartRepository.findById(removedCartItem.getId())
                .orElseThrow(CartItemNotExist::new);
        User currentUser = authService.getCurrentUser();
        if (cartItem.getUser() == currentUser) {
            cartRepository.deleteById(removedCartItem.getId());
        } else {
            throw new UnauthorizedAction();
        }
    }

    public void clearAllCart() {
        User currentUser = authService.getCurrentUser();
        cartRepository.deleteAllByUser(currentUser);
    }
}
