package com.amaghrabi.security.model;

import lombok.Data;

import java.util.List;

@Data
public class FawryRequest {
    private String merchantCode;
    private String merchantRefNum;
    private String customerProfileId;
    private String returnUrl;
    private List<ChargeItem> chargeItems;
    private String secureHashKey;

    @Data
    public static class ChargeItem {
        private String itemId;
        private int quantity;
        private double price;
    }
}
