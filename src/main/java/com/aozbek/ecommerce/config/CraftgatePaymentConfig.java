package com.aozbek.ecommerce.config;

import io.craftgate.Craftgate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CraftgatePaymentConfig {

    @Value("${craftgate.api.key}")
    private String craftgateApiKey;
    @Value("${craftgate.secret.key}")
    private String craftgateSecretKey;
    @Value("${craftgate.test.url}")
    private String craftgateTestUrl;

    @Bean
    public Craftgate craftgate() {
        return new Craftgate(craftgateApiKey, craftgateSecretKey, craftgateTestUrl);
    }
}
