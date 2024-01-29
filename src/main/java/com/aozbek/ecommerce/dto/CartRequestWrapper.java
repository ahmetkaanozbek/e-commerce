package com.aozbek.ecommerce.dto;

import com.aozbek.ecommerce.model.Product;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartRequestWrapper {
    private Product product;
    private Integer quantity;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        CartRequestWrapper that = (CartRequestWrapper) obj;
        return (Objects.equals(quantity, that.quantity));
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }
}
