package com.dmsacad.store.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
public class ProductDto {
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Byte categoryId;
}
