package com.dmsacad.store.dtos.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal cartTotalPrice = BigDecimal.ZERO;
}
