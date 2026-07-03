package com.dmsacad.store.services.helpers;

import com.dmsacad.store.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private long orderId;
    private PaymentStatus paymentStatus;
}
