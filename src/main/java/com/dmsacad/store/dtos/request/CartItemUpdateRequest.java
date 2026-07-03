package com.dmsacad.store.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;

@Data
public class CartItemUpdateRequest {
    @Positive(message = "Product ID must be a positive Integer")
    private long productId;

    @NotNull(message = "Cart id is not null. Must be provided")
    @NotBlank(message = "The card id is required. It is not blank")
    private String cartId;

    @NotNull(message = "Quantity must be provided")
    @Min(value = 1, message = "Quantity must be greather or equals to 1")
    @Max(value = 100, message = "Quantity must be >=1 and <= 100")
    private Integer quantity;
}
