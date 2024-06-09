package com.amaghrabi.security.controller;

import com.amaghrabi.security.dto.PaymobDetails;
import com.amaghrabi.security.service.PayMob;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/paymob-payment")
public class PayMobController {
    private final PayMob payMob;

    public PayMobController(PayMob payMob) {
        this.payMob = payMob;
    }

    @PostMapping("/create")
    public String createPaymobIntent(@RequestBody PaymobDetails paymobDetails) {
        try {
            return payMob.createPaymentIntent(paymobDetails);
        } catch (IOException e) {
            return "Failed to create payment intent: " + e.getMessage();
        }
    }
}
