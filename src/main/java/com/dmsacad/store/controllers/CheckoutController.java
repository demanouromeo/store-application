package com.dmsacad.store.controllers;

import com.dmsacad.store.dtos.response.CheckoutResponse;
import com.dmsacad.store.dtos.request.CheckoutRequest;
import com.dmsacad.store.services.CheckoutService;
import com.dmsacad.store.services.helpers.WebhookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse checkout(
            @Valid @RequestBody CheckoutRequest request
            ){
        return checkoutService.checkout(request);
            //return ResponseEntity.ok(checkoutService.checkout(request));

    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String, String> headers,
            @RequestBody String payload //Json object telling us abort what happens
    )  {
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }
}
