package com.dmsacad.store.dtos.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 80, message = "The product name is between [2 and 80]")
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    @NotNull(message = "Product description is required")
    private String description;

    @Positive
    @NotNull
    private Byte categoryId;
}
