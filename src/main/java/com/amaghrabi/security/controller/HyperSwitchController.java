package com.amaghrabi.security.controller;

import com.amaghrabi.security.service.HyperSwitchService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hyper-switch-payment")
public class HyperSwitchController {

    private final HyperSwitchService hyperSwitchService;

    public HyperSwitchController(HyperSwitchService hyperSwitchService) {
        this.hyperSwitchService = hyperSwitchService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPayment() throws JSONException {
        String payload = "{ \"amount\": 100, \"currency\": \"USD\" }";
        String responseString = hyperSwitchService.createPayment(payload);
        JSONObject responseJson = new JSONObject(responseString);

        String clientSecret = responseJson.getString("client_secret");

        JSONObject finalResponse = new JSONObject();
        finalResponse.put("clientSecret", clientSecret);

        return ResponseEntity.ok(finalResponse.toString());
    }
}
