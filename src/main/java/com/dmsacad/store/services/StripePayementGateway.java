package com.dmsacad.store.services;

import com.dmsacad.store.entities.Order;
import com.dmsacad.store.entities.OrderItem;
import com.dmsacad.store.entities.PaymentStatus;
import com.dmsacad.store.exceptions.PaymentException;
import com.dmsacad.store.services.helpers.CheckoutSession;
import com.dmsacad.store.services.helpers.PaymentResult;
import com.dmsacad.store.services.helpers.WebhookRequest;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StripePayementGateway implements PaymentGateway {
    @Value("${websiteUrl}")
    private String websiteUrl;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())//Client is redirected to this URL if payment is successfull
                    .setCancelUrl(websiteUrl + "/checkout-cancel.html")//Client is redirected to this URL if payment is CANCELLED
                    .putMetadata("order_id", order.getId().toString());
            order.getItems().forEach(item -> {
                        var lineIem = createLineItem(item);
                        builder.addLineItem(lineIem);
                    }
            );

            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            throw new PaymentException("Couldn't deserialize stripe event. Check the SDK and API version");
        }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try {
            var payload = request.getPayload();
            var signature = request.getHeaders().get("stripe-signature");
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);
            //System.out.println(event.getType());


            //if event is 'charge' then we cast -> (Charge) StripeObject
            //if event is 'payment_intent.succeeded' then we cast -> (Payment) StripeObject
            return switch (event.getType()) {
                case "payment_intent.succeeded" ->
                    //Update order status (PAID)
                    Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));

                case "payment_intent.payment_failed" ->
                    //Update payment status (FAILED)
                    Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));

                default -> Optional.empty();
            };
        } catch (SignatureVerificationException e) {
            throw new PaymentException("Invalid sgnature"); //The signature is incorrect
        }
    }

    private Long extractOrderId(Event event) {
        var stripeObject = event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(
                        () -> new PaymentException("Couldn't deserialize stripe event. Check the SDK and API version")
                );
        var paymentIntent = (PaymentIntent) stripeObject;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPriceData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(creatProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData creatProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
