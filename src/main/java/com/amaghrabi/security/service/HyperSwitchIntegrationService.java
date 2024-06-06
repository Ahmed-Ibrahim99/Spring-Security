package com.amaghrabi.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Service
public class HyperSwitchIntegrationService {
    @Value("${hyperswitch.api.key}")
    private String apiKey;

    @Value("${hyperswitch.api.base-url}")
    private String apiBaseUrl;

    public String PaymentMethod(String payload) {
        try {
            URL url = new URL(apiBaseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("api-key", apiKey);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
            } else {
                return "HTTP request failed with response code: " + responseCode;
            }
        } catch (IOException e) {
            return e.getMessage();
//        } catch (ProtocolException e) {
//            throw new RuntimeException(e);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
}
