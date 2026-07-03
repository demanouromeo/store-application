package com.dmsacad.store.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckoutRequest {
    @NotNull(message = "Cart ID is required")
    private String cartId;
}
