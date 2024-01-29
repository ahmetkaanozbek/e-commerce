package com.aozbek.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private BigDecimal price;
    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product that = (Product) obj;
        return (Objects.equals(productName, that.productName)) && (Objects.equals(price, that.price))
                && (Objects.equals(description, that.description)) && (Objects.equals(category, that.category));
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, price, description, category);
    }
}
