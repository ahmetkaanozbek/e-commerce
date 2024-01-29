package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.dto.CartRequestWrapper;
import com.aozbek.ecommerce.dto.GetUserCartDto;
import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.model.Product;
import com.aozbek.ecommerce.model.User;
import com.aozbek.ecommerce.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = CartController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = CartController.class)
@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private CartService cartService;

    @Test
    void getAllItems_CheckIfItReturnsGetUserCartDtoListAndIfTheHttpStatusCodeIsOk() throws Exception {
        // given
        Product testProduct = new Product(2L, "testProduct", new BigDecimal(10), "description", null);
        GetUserCartDto testGetUserCartDto = new GetUserCartDto(1L, 10, testProduct);
        List<GetUserCartDto> testGetUserCartDTOs = new ArrayList<>();
        testGetUserCartDTOs.add(testGetUserCartDto);

        // mockitoBehavior
        when(cartService.getAllItems())
                .thenReturn(testGetUserCartDTOs);

        // when
        mockMvc.perform(get("/api/cart/get-cart"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testGetUserCartDTOs)));

        // then
        verify(cartService, times(1)).getAllItems();
    }

    @Test
    void addItemToCart_VerifyIfObjectInRequestIsSameWithObjectWhichIsUsedByCartServiceAndResponseIsTheExpectedOne()
            throws Exception {
        // given
        Product testProduct = new Product(2L, "testProduct", new BigDecimal(10), "description", null);
        CartRequestWrapper testCartRequestWrapper = new CartRequestWrapper(testProduct, 1);

        // when
        mockMvc.perform(post("/api/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCartRequestWrapper)))
                .andExpect(status().isCreated())
                .andExpect(content().string("An item has been added to cart successfully."));

        // then
        verify(cartService, times(1)).addItemToCart(testCartRequestWrapper);
    }

    @Test
    void updateCartItem_VerifyIfObjectInRequestIsSameWithObjectWhichIsUsedByCartServiceAndResponseIsTheExpectedOne()
            throws Exception {
        // given
        Product testProduct = new Product(2L, "testProduct", new BigDecimal(10), "description", null);
        User testUser = new User(2L, "testUsername", "testPassword", null);
        CartItem testCartItem = new CartItem(1L, 1, testProduct, testUser);

        // when
        mockMvc.perform(patch("/api/cart/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCartItem)))
                .andExpect(status().isOk())
                .andExpect(content().string("An items quantity has been updated successfully."));

        // then
        verify(cartService, times(1)).updateCartItem(testCartItem);
    }

    @Test
    void removeCartItem_VerifyIfObjectInRequestIsSameWithObjectWhichIsUsedByCartServiceAndResponseIsTheExpectedOne()
            throws Exception {
        // given
        Product testProduct = new Product(2L, "testProduct", new BigDecimal(10), "description", null);
        User testUser = new User(2L, "testUsername", "testPassword", null);
        CartItem testCartItem = new CartItem(1L, 1, testProduct, testUser);

        // when
        mockMvc.perform(delete("/api/cart/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCartItem)))
                .andExpect(status().isOk())
                .andExpect(content().string("An item has been deleted successfully."));

        // then
        verify(cartService, times(1)).removeCartItem(testCartItem);
    }

    @Test
    void clearAllCart_CheckResponseIsTheExpectedOne() throws Exception {
        // when
        mockMvc.perform(delete("/api/cart/clear"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cart has been cleared successfully."));

        // then
        verify(cartService, times(1)).clearAllCart();
    }
}












