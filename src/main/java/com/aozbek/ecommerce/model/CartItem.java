package com.aozbek.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "users_id")
    User user;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        CartItem that = (CartItem) obj;
        return (Objects.equals(quantity, that.quantity)) && (Objects.equals(product, that.product))
                && (Objects.equals(user, that.user));
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, product, user);
    }
}
