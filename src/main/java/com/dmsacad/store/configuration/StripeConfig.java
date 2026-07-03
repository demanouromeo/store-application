package com.dmsacad.store.configuration;

import com.stripe.Stripe;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
//@ConfigurationProperties(prefix = "stripe")
@Data
public class StripeConfig {
    @Value("${stripe.secretKey}")
    private String secretKey;

    // @Value("${stripe.publicKey}")
    // private String publicKey;

    @PostConstruct//Tells Spring to call this method when this bean is created
    public void init(){
        Stripe.apiKey = secretKey;
    }
}
