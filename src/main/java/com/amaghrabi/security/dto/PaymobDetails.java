package com.amaghrabi.security.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PaymobDetails {
    private int amount;
    private String currency;
    private List<Object> payment_methods;
    private List<Item> items;
    private BillingData billing_data;
    private Customer customer;
    private Map<String, Object> extras;

    @Data
    public static class Item {
        private String name;
        private int amount;
        private String description;
        private int quantity;
    }

    @Data
    public static class BillingData {
        private String apartment;
        private String first_name;
        private String last_name;
        private String street;
        private String building;
        private String phone_number;
        private String country;
        private String email;
        private String floor;
        private String state;

    }

    @Data
    public static class Customer {
        private String first_name;
        private String last_name;
        private String email;
        private Map<String, Object> extras;

    }
}
