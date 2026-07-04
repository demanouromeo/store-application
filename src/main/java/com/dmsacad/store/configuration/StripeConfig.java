package com.dmsacad.store.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Configuration
//@ConfigurationProperties(prefix = "stripe")
@Data
public class StripeConfig {

    @Value("${stripe.secretKey}")
    private String secretKey;

    @PostConstruct//Tells Spring to call this method when this bean is created
    public void init() {
        Stripe.apiKey = secretKey;
    }
}
