package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.dto.CartRequestWrapper;
import com.aozbek.ecommerce.exception.CartItemNotExist;
import com.aozbek.ecommerce.exception.ProductNotExist;
import com.aozbek.ecommerce.exception.UnauthorizedAction;
import com.aozbek.ecommerce.mapper.GetUserCartMapper;
import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.model.Product;
import com.aozbek.ecommerce.model.User;
import com.aozbek.ecommerce.repository.CartRepository;
import com.aozbek.ecommerce.repository.ProductRepository;
import com.aozbek.ecommerce.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService underTestService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private AuthService authService;
    @Mock
    private GetUserCartMapper getUserCartMapper;
    @Mock
    private ProductRepository productRepository;

    @Test
    void getAllItems_getAllItemsSuccessfully() {
        // given
        User testUser = new User(1L, "testUser", "password", null);
        Product testProduct = new Product(2L, "productName", new BigDecimal(15), "singleProduct", null);
        CartItem testCartItem = new CartItem(3L, 10, testProduct, testUser);
        List<CartItem> testCartItems = new ArrayList<>();
        testCartItems.add(testCartItem);

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(userRepository.getUserByUsername(testUser.getUsername()))
                .thenReturn(testUser);
        when(cartRepository.getAllByUser(testUser))
                .thenReturn(testCartItems);

        // when
        underTestService.getAllItems();

        // then
        verify(userRepository).getUserByUsername(testUser.getUsername());
        verify(authService).getCurrentUser();
        verify(cartRepository).getAllByUser(testUser);
        verify(getUserCartMapper).map(testCartItems);
    }

    @Test
    void addItemToCart_addNotAlreadyAddedAndValidItemToCartSuccessfullyAndIfTheSavedCartItemSameWithIntendedOne() {
        // given
        Product testProduct = new Product(1L, "sampleProduct", new BigDecimal(15), "productForTestPurposes", null);
        User testUser = new User(2L, "testUsername", "testPassword", null);
        CartRequestWrapper testCartRequestWrapper = new CartRequestWrapper(testProduct, 10);

        // mockingBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(productRepository.existsById(testProduct.getId()))
                .thenReturn(true);
        when(cartRepository.findByUserAndProduct(testUser, testProduct))
                .thenReturn(null);

        // when
        underTestService.addItemToCart(testCartRequestWrapper);

        // then
        ArgumentCaptor<CartItem> cartItemArgumentCaptor = ArgumentCaptor.forClass(CartItem.class);
        // verify save method is called on cartRepository
        verify(cartRepository).save(cartItemArgumentCaptor.capture());
        CartItem capturedCartItem = cartItemArgumentCaptor.getValue();
        assertThat(capturedCartItem.getQuantity()).isEqualTo(testCartRequestWrapper.getQuantity());
        assertThat(capturedCartItem.getProduct()).isEqualTo(testProduct);
        assertThat(capturedCartItem.getUser()).isEqualTo(testUser);
    }

    @Test
    void addItemToCart_throwsProductNotExistExceptionIfNoProductsExistByThatId() {
        // given
        Product testProduct = new Product(1L, "sampleProduct", new BigDecimal(15), "productForTestPurposes", null);
        CartRequestWrapper cartRequestWrapper = new CartRequestWrapper(testProduct, 1);

        // mockingBehavior
        when(productRepository.existsById(testProduct.getId()))
                .thenReturn(false);

        // when/then
        // Exception is thrown and instance of a CartItem is never saved.
        assertThrows(ProductNotExist.class, () -> underTestService.addItemToCart(cartRequestWrapper));
        verify(cartRepository, never()).save(any(CartItem.class));
    }

    @Test
    void addItemToCart_addQuantityToTheExistingCartItemsQuantityWhichAlreadyExistsInCart() {
        // given
        Product testProduct = new Product(1L, "sampleProduct", new BigDecimal(15), "productForTestPurposes", null);
        User testUser = new User(2L, "testUsername", "testPassword", null);
        CartItem existingCartItem = new CartItem(3L, 1, testProduct, testUser);
        CartRequestWrapper cartRequestWrapper = new CartRequestWrapper(testProduct, 3);
        Integer updatedQuantity = existingCartItem.getQuantity() + cartRequestWrapper.getQuantity();

        // mockingBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(productRepository.existsById(testProduct.getId()))
                .thenReturn(true);
        when(cartRepository.findByUserAndProduct(testUser, testProduct))
                .thenReturn(existingCartItem);

        // when
        underTestService.addItemToCart(cartRequestWrapper);

        // then
        // Verify if the quantity is added to the existing items quantity correctly
        // and save method is called on cartRepository.
        assertThat(existingCartItem.getQuantity()).isEqualTo(updatedQuantity);
        verify(cartRepository).save(existingCartItem);
    }

    @Test
    void updateCartItem_updateAnItemsQuantityWhichAlreadyExistsInCart() {
        // given
        Product testProduct = new Product(1L, "sampleProduct", new BigDecimal(15), "productForTestPurposes", null);
        User testUser = new User(2L, "testUsername", "testPassword", null);
        CartItem existingCartItem = new CartItem(3L, 1, testProduct, testUser);
        CartItem updatedCartItem = new CartItem(3L, 15, testProduct, testUser);

        // mockingBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(cartRepository.findByUserAndId(testUser, updatedCartItem.getId()))
                .thenReturn(existingCartItem);

        // when
        underTestService.updateCartItem(updatedCartItem);

        // then
        // Check if quantity updated correctly and save method is called on cartRepository
        assertThat(existingCartItem.getQuantity()).isEqualTo(updatedCartItem.getQuantity());
        verify(cartRepository).save(existingCartItem);
    }

    @Test
    void updateCartItem_throwsCartItemNotExistExceptionWhenThereIsNoCartItemWithThatUserAndCartItemId() {
        // given
        Product testProduct = new Product(1L, "sampleProduct", new BigDecimal(15), "productForTestPurposes", null);
        User testUser = new User(2L, "testUsername", "testPassword", null);
        CartItem existingCartItem = new CartItem(3L, 1, testProduct, testUser);

        // mockingBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(cartRepository.findByUserAndId(testUser, existingCartItem.getId()))
                .thenReturn(null);

        // when/then
        // Exception is thrown and instance of a CartItem is never saved.
        assertThrows(CartItemNotExist.class, () -> underTestService.updateCartItem(existingCartItem));
        verify(cartRepository, never()).save(existingCartItem);
    }

    @Test
    void removeCartItem_checkIfItRemovesCartItemSuccessfully() {
        // given
        Product testProduct = new Product(1L, "sampleProduct", new BigDecimal(15), "productForTestPurposes", null);
        User testUser = new User(2L, "testUsername", "testPassword", null);
        CartItem existingCartItem = new CartItem(3L, 1, testProduct, testUser);

        // mockingBehavior
        when(cartRepository.findById(existingCartItem.getId()))
                .thenReturn(Optional.of(existingCartItem));
        when(authService.getCurrentUser())
                .thenReturn(testUser);

        // when
        underTestService.removeCartItem(existingCartItem);

        // then
        verify(cartRepository).deleteById(existingCartItem.getId());
    }

    @Test
    void removeCartItem_checkCartItemNotExistExceptionIsThrownWhenThereIsNoPersistedCartItemWithThatId() {
        // given
        Product testProduct = new Product(1L, "sampleProduct", new BigDecimal(15), "productForTestPurposes", null);
        User testUser = new User(2L, "testUsername", "testPassword", null);
        CartItem removedCartItem = new CartItem(3L, 1, testProduct, testUser);

        // mockingBehavior
        when(cartRepository.findById(removedCartItem.getId()))
                .thenThrow(CartItemNotExist.class);

        // when/then
        assertThrows(CartItemNotExist.class, () -> underTestService.removeCartItem(removedCartItem));
        verify(cartRepository, never()).deleteById(removedCartItem.getId());
    }

    @Test
    void removeCartItem_checkUnauthorizedActionExceptionIsThrownIfCurrentUserDoesntMatchWithTheGivenOne() {
        // given
        Product testProduct = new Product(1L, "sampleProduct", new BigDecimal(15), "productForTestPurposes", null);
        User testUser = new User(2L, "testUsername", "testPassword", null);
        User differentUser = new User(4L, "differentUsername", "differentPassword", null);
        CartItem removedCartItem = new CartItem(3L, 1, testProduct, differentUser);

        // mockingBehavior
        when(cartRepository.findById(removedCartItem.getId()))
                .thenReturn(Optional.of(removedCartItem));
        when(authService.getCurrentUser())
                .thenReturn(testUser);

        // when/then
        assertThrows(UnauthorizedAction.class, () -> underTestService.removeCartItem(removedCartItem));
    }

    @Test
    void clearAllCart_checkIfAllCartItemsHaveBeenDeleted() {
        // given
        User testUser = new User(2L, "testUsername", "testPassword", null);

        // mockingBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);

        // when
        underTestService.clearAllCart();

        // then
        verify(cartRepository).deleteAllByUser(testUser);
    }
}