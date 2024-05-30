package com.amaghrabi.security.gateway;

import com.amaghrabi.security.model.PaymentRequest;
import com.amaghrabi.security.model.PaymentResponse;
import com.amaghrabi.security.service.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class MastercardPaymentGateway implements PaymentGateway {

    public boolean supports(String cardNumber) {
        // Mastercard cards start with a 5
        return cardNumber.startsWith("5");
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Implement Mastercard payment processing logic
        System.out.println("Processing payment using Mastercard gateway...");
        System.out.println("Amount: " + paymentRequest.getAmount());
        System.out.println("Card Number: " + paymentRequest.getCardNumber());
        System.out.println("Card Holder Name: " + paymentRequest.getCardHolderName());
        System.out.println("Expiration Date: " + paymentRequest.getExpirationDate());
        System.out.println("CVV: " + paymentRequest.getCvv());
        System.out.println("Currency: " + paymentRequest.getCurrency());
        System.out.println("Billing Address: " + paymentRequest.getBillingAddress());

        // Simulate successful payment processing
        return new PaymentResponse(true, "Payment processed successfully");
    }
}
