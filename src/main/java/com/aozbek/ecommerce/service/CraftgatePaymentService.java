package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.dto.CardInformation;
import com.aozbek.ecommerce.model.CartItem;
import com.aozbek.ecommerce.model.User;
import com.aozbek.ecommerce.repository.CartRepository;
import io.craftgate.Craftgate;
import io.craftgate.model.Currency;
import io.craftgate.model.PaymentGroup;
import io.craftgate.model.PaymentPhase;
import io.craftgate.request.CreatePaymentRequest;
import io.craftgate.request.dto.Card;
import io.craftgate.request.dto.PaymentItem;
import io.craftgate.response.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CraftgatePaymentService {

    private final Craftgate craftgate;
    private final AuthService authService;
    private final CartRepository cartRepository;

    public CraftgatePaymentService(
            Craftgate craftgate,
            AuthService authService,
            CartRepository cartRepository) {
        this.craftgate = craftgate;
        this.authService = authService;
        this.cartRepository = cartRepository;
    }

    public void processPayment(CardInformation cardInformation) {

        CreatePaymentRequest request = buildPaymentRequest(cardInformation);
        PaymentResponse response = craftgate.payment().createPayment(request);
        log.info(String.valueOf(response));
    }

    public CreatePaymentRequest buildPaymentRequest(CardInformation cardInformation) {
        List<PaymentItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // No need to check if Authentication is null due to payment can only be done by authenticated user.
        User currentUser = authService.getCurrentUser();
        List<CartItem> cartItems = cartRepository.getAllByUser(currentUser);

        for (CartItem cartItem : cartItems) {
            BigDecimal itemPrice = cartItem.getProduct().getPrice();
            totalAmount = totalAmount.add(itemPrice);

            items.add(PaymentItem.builder()
                    .name(cartItem.getProduct().getProductName())
                    .externalId(UUID.randomUUID().toString())
                    .price(cartItem.getProduct().getPrice())
                    .build());
        }

        // Creating and returning an instance of CreatePaymentRequest.class which is provided in Craftgate SDK.
        return CreatePaymentRequest.builder()
                .price(totalAmount)
                .paidPrice(totalAmount)
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId("456d1297-908e-4bd6-a13b-4be31a6e47d5")
                .paymentGroup(PaymentGroup.PRODUCT)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .cardHolderName(cardInformation.getCardHolderName())
                        .cardNumber(cardInformation.getCardNumber())
                        .expireYear(cardInformation.getExpireYear())
                        .expireMonth(cardInformation.getExpireMonth())
                        .cvc(cardInformation.getCvc())
                        .build())
                .items(items)
                .build();
    }
}
