package com.aozbek.ecommerce.dto;

import com.aozbek.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartRequestWrapper {
    private Product product;
    private Integer quantity;
}
