package com.dmsacad.store.services;

import com.dmsacad.store.entities.Order;
import com.dmsacad.store.services.helpers.CheckoutSession;
import com.dmsacad.store.services.helpers.PaymentResult;
import com.dmsacad.store.services.helpers.WebhookRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
