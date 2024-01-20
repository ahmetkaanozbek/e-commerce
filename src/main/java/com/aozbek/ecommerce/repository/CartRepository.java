package com.aozbek.ecommerce.repository;

import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.model.Product;
import com.aozbek.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);
    CartItem findByUserAndId(User user, Long id);
    void deleteAllByUser(User user);
    List<CartItem> getAllByUser(User user);
}
