package com.amaghrabi.security.controller;

import com.amaghrabi.security.gateway.GenericPaymentGateway;
import com.amaghrabi.security.model.PaymentRequest;
import com.amaghrabi.security.model.PaymentResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final GenericPaymentGateway paymentGateway;

    public PaymentController(GenericPaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse response = paymentGateway.processPayment(paymentRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
