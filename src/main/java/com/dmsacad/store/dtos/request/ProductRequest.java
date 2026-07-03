package com.dmsacad.store.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 80, message = "The product name is between [2 and 80]")
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    private String description;

    @Positive
    @NotNull
    private Byte categoryId;
}
