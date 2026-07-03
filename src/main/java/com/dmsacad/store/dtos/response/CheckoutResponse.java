package com.dmsacad.store.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

//@AllArgsConstructor
@Data
public class CheckoutResponse {
    private Long orderId;
    private String checkoutUrl;

    public CheckoutResponse(Long orderId, String checkoutUrl){
        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }
}
