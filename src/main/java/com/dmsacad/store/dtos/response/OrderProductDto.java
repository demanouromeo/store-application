package com.dmsacad.store.dtos.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductDto {//THIS CLASS is similar to CartProductDto. But has been created for changes in cartproduct don't affect product in order
    private long id;
    private String name;
    private BigDecimal price;
}
