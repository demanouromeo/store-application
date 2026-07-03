package com.dmsacad.store.services;

import com.dmsacad.store.dtos.request.CheckoutRequest;
import com.dmsacad.store.dtos.response.CheckoutResponse;
import com.dmsacad.store.entities.Order;
import com.dmsacad.store.entities.PaymentStatus;
import com.dmsacad.store.exceptions.CartEmptyException;
import com.dmsacad.store.exceptions.CartNotFoundException;
import com.dmsacad.store.exceptions.PaymentException;
import com.dmsacad.store.repositories.CartRepository;
import com.dmsacad.store.repositories.OrderRepository;
import com.dmsacad.store.services.helpers.PaymentResult;
import com.dmsacad.store.services.helpers.WebhookRequest;
import com.dmsacad.store.tools.Utility;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;




    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.findCartWithItems(Utility.hexStringToUUID(request.getCartId())).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException(); //Return badrequest car not found
        }

        if (cart.getCartItems().isEmpty()) {
            throw new CartEmptyException();
        }
        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        //-------------------------- CREATION OF STRIPE CHECKOUT -------------------------------------//
        try {
            var session = paymentGateway.createCheckoutSession(order);
            //------------------------- END CREATION OF STRIPE CHECKOUT ----------------------------------//

            cartService.clearCart2(cart.getId());
            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        }catch (PaymentException e){
            orderRepository.delete(order);
            throw e;
        }
    }

    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult ->{
                    var order = orderRepository
                            .findById(paymentResult.getOrderId())
                            .orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());//instead of PaymentStatus.PAID
                    orderRepository.save(order);
                });
    }
}
