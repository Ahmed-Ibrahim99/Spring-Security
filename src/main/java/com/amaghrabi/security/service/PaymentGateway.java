package com.amaghrabi.security.service;

import com.amaghrabi.security.model.PaymentRequest;
import com.amaghrabi.security.model.PaymentResponse;

public interface PaymentGateway {
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
