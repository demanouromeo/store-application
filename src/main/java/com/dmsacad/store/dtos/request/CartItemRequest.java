package com.dmsacad.store.dtos.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemRequest {
    @Positive(message = "Product ID must be a positive Integer")
    private long productId;
}
