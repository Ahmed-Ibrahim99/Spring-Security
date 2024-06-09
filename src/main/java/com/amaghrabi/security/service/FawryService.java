package com.amaghrabi.security.service;

import com.amaghrabi.security.model.FawryRequest;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class FawryService {

    public String generateSignature(FawryRequest request) throws NoSuchAlgorithmException {
        StringBuilder signatureStringBuilder = new StringBuilder();
        signatureStringBuilder.append(request.getMerchantCode())
                .append(request.getMerchantRefNum())
                .append(request.getCustomerProfileId() != null ? request.getCustomerProfileId() : "")
                .append(request.getReturnUrl());

        for (FawryRequest.ChargeItem item : request.getChargeItems()) {
            String itemId = item.getItemId();
            String quantity = String.valueOf(item.getQuantity());
            String price = String.format("%.2f", item.getPrice());

            signatureStringBuilder.append(itemId)
                    .append(quantity)
                    .append(price);
        }

        signatureStringBuilder.append(request.getSecureHashKey());

        String signatureString = signatureStringBuilder.toString();

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(signatureString.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();

        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
