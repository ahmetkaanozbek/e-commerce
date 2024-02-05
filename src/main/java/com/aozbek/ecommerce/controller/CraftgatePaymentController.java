package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.dto.CardInformation;
import com.aozbek.ecommerce.service.CraftgatePaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/craftgate-payment")
public class CraftgatePaymentController {

    private final CraftgatePaymentService craftgatePaymentService;

    public CraftgatePaymentController(CraftgatePaymentService craftgatePaymentService) {
        this.craftgatePaymentService = craftgatePaymentService;
    }

    @PostMapping(value = "/pay")
    public ResponseEntity<String> createPayment(@RequestBody CardInformation cardInformation) {
        craftgatePaymentService.processPayment(cardInformation);
        return ResponseEntity.status(HttpStatus.OK).body("Payment has been done successfully.");
    }
}
