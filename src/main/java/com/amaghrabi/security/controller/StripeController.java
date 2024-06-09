package com.amaghrabi.security.controller;

import com.amaghrabi.security.service.StripeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripe-payment")
public class StripeController {
    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-payment-intent")
    public String createPaymentIntent() throws JsonProcessingException {
        return stripeService.createStripePayment();
    }

}
