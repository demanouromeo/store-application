package com.dmsacad.store.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartItemDto {
    CartProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;

}
