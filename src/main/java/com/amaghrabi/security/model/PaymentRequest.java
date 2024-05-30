package com.amaghrabi.security.model;

import lombok.Data;

@Data
public class PaymentRequest {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    private double amount;
    private String currency;
    private String billingAddress;
}
