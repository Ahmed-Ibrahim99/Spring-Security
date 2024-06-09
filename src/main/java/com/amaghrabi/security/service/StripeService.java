package com.amaghrabi.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {
    @Value("${stripe.api.key}")
    String stripeKey;
    private final String stripeApiUrl = "https://api.stripe.com/v1/payment_intents";

    public String createStripePayment() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(stripeKey);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("amount", "3000");
        params.add("currency", "EGP");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.exchange(stripeApiUrl, HttpMethod.POST, request, Map.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                String clientSecret = (String) responseBody.get("client_secret");
                if (clientSecret != null) {
                    // Create a Map to represent the JSON object
                    Map<String, String> jsonMap = new HashMap<>();
                    jsonMap.put("client_secret", clientSecret);
                    // Serialize the Map to JSON string
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.writeValueAsString(jsonMap);
                }
            }
        }
        throw new RuntimeException("Failed to create payment intent");

    }


}