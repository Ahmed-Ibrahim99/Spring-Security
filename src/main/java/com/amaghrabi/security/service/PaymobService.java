package com.amaghrabi.security.service;

import com.amaghrabi.security.model.PaymobRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Service
public class PaymobService {

    @Value("${paymob.api.key}")
    private String apiKey;

    @Value("${paymob.api.base-url}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate;

    public PaymobService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createPaymentIntent(PaymobRequest paymobRequest) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<PaymobRequest> request = new HttpEntity<>(paymobRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiBaseUrl,
                HttpMethod.POST,
                request,
                String.class);

        return response.getBody();
    }
}
