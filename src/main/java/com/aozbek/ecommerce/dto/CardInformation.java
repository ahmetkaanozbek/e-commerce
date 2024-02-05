package com.aozbek.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardInformation {
    private String cardHolderName;
    private String cardNumber;
    private String expireYear;
    private String expireMonth;
    private String cvc;
}
