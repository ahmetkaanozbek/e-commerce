package com.aozbek.ecommerce.repository;

import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.model.Product;
import com.aozbek.ecommerce.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CartRepositoryTest {

    private final CartRepository underTest;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    CartRepositoryTest(CartRepository underTest,
                       UserRepository userRepository,
                       ProductRepository productRepository) {
        this.underTest = underTest;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Test
    void findByUserAndProduct() {
        // given
        User testUser = new User(1L, "testUser", "testPassword", null);
        Product testProduct = new Product(1L, "testProduct", new BigDecimal(15), "description", null);
        CartItem testCartItem = new CartItem(1L, 3, testProduct, testUser);

        userRepository.save(testUser);
        productRepository.save(testProduct);
        CartItem realCartItem = underTest.save(testCartItem);

        // when
        CartItem expected = underTest.findByUserAndProduct(testUser, testProduct);

        // then
        assertThat(expected).isEqualTo(realCartItem);
    }
}