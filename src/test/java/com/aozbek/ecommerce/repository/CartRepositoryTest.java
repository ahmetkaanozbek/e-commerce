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
        User user = new User(
                1L,
                "kaan",
                "123",
                null
        );

        BigDecimal bigDecimal = new BigDecimal(15);

        Product product = new Product(
                1L,
                "nuts",
                bigDecimal,
                "A delicious one.",
                null
        );

        CartItem underTestCartItem = new CartItem(
                1L,
                3,
                product,
                user
        );

        userRepository.save(user);
        productRepository.save(product);
        CartItem realCartItem = underTest.save(underTestCartItem);

        // when
        CartItem expected = underTest.findByUserAndProduct(user, product);

        // then
        assertThat(expected).isEqualTo(realCartItem);
    }
}