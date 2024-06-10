package com.amaghrabi.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class HyperSwitchService {

    public HyperSwitchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${hyperswitch.api.key}")
    private String apiKey;

    @Value("${hyperswitch.api.base-url}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate;

    private static final String STATIC_PAYLOAD = "{ \"amount\": 100, \"currency\": \"USD\" }"; // Example payload

    public String createPayment() {
        try {
            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("api-key", apiKey);

            // Prepare the request entity with static payload
            HttpEntity<String> request = new HttpEntity<>(STATIC_PAYLOAD, headers);

            // Make the POST request
            ResponseEntity<String> response = restTemplate.postForEntity(apiBaseUrl, request, String.class);

            // Check the response status
            HttpStatus statusCode = (HttpStatus) response.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                return response.getBody();
            } else {
                return "HTTP request failed with response code: " + statusCode.value();
            }
        } catch (HttpClientErrorException e) {
            return "HTTP request failed: " + e.getMessage();
        }
    }
}
