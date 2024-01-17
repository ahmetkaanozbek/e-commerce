package com.aozbek.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedProductDto {
    private Long id;
    private String productName;
    private BigDecimal price;
    private String description;
    private Long categoryId;
}
