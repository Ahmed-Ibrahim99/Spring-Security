package com.amaghrabi.security.service;

import com.amaghrabi.security.dto.PaymobDetails;
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
public class PayMob{
        @Value("${paymob.api.key}")
        private String apiKey;

        @Value("${paymob.api.base-url}")
        private String apiBaseUrl;

        private final RestTemplate restTemplate;

        public PayMob(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public String createPaymentIntent(PaymobDetails paymobDetails) throws IOException {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<PaymobDetails> request = new HttpEntity<>(paymobDetails, headers);

            ResponseEntity<String> response = restTemplate.exchange(apiBaseUrl,
                    HttpMethod.POST,
                    request,
                    String.class);

            return response.getBody();
        }
}