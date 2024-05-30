package com.amaghrabi.security.gateway;

import com.amaghrabi.security.model.PaymentRequest;
import com.amaghrabi.security.model.PaymentResponse;
import com.amaghrabi.security.service.PaymentGateway;
import org.springframework.stereotype.Component;

@Component
public class VisaPaymentGateway implements PaymentGateway {

    public boolean supports(String cardNumber) {
        // Visa cards start with a 4
        return cardNumber.startsWith("4");
    }
    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Implement Visa payment processing logic
        System.out.println("Processing payment using Visa gateway...");
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
