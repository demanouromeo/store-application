package com.dmsacad.store.exceptions;

public class OrderNotFoundException extends Throwable {
    public OrderNotFoundException(){
        super("Order not found");
    }
}
