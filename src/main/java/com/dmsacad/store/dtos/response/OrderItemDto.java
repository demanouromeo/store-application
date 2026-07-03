package com.dmsacad.store.dtos.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDto {

    private OrderProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
