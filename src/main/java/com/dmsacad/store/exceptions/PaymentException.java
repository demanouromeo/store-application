package com.dmsacad.store.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentException extends RuntimeException {
    public PaymentException(String s){
        super(s);
    }
}
