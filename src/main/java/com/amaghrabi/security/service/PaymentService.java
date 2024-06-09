package com.amaghrabi.security.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public PaymentIntent createPaymentIntent(Long amount) throws StripeException {
//        System.out.println("testPaymentIntent++++++++");
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();
//        System.out.println("testPaymentIntent++++++++"+params.toString());
        return PaymentIntent.create(params);
    }
}


