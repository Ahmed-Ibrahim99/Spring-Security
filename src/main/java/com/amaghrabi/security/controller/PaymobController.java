package com.amaghrabi.security.controller;

import com.amaghrabi.security.model.PaymobRequest;
import com.amaghrabi.security.service.PaymobService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
@RequestMapping("/paymob-payment")
public class PaymobController {
    private final PaymobService paymobService;

    public PaymobController(PaymobService paymobService) {
        this.paymobService = paymobService;
    }

    @PostMapping("/create")
    public String createPaymobIntent(@RequestBody PaymobRequest paymobRequest) {
        try {
            return paymobService.createPaymentIntent(paymobRequest);
        } catch (IOException e) {
            return "Failed to create payment intent: " + e.getMessage();
        }
    }
}
