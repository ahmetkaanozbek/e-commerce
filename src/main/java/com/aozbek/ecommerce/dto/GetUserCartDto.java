package com.aozbek.ecommerce.dto;

import com.aozbek.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserCartDto {
    private Long id;
    private Integer quantity;
    private Product product;
}
