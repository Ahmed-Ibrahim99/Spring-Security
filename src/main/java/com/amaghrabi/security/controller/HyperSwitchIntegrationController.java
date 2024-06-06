package com.amaghrabi.security.controller;

import com.amaghrabi.security.service.HyperSwitchIntegrationService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@PreAuthorize("hasRole('USER')")
@RequestMapping("/hyperswitch-payment")

public class HyperSwitchIntegrationController {


    private final HyperSwitchIntegrationService hyperSwitchIntegrationService;
    public HyperSwitchIntegrationController(HyperSwitchIntegrationService hyperSwitchIntegrationService){
        this.hyperSwitchIntegrationService = hyperSwitchIntegrationService ;
    }

    @PostMapping("/create")
    public ResponseEntity<?> PaymentMethod() throws JSONException {
        String payload = "{ \"amount\": 100, \"currency\": \"USD\" }";
        String responseString = hyperSwitchIntegrationService.PaymentMethod(payload);
        JSONObject responseJson = new JSONObject(responseString);

        String clientSecret = responseJson.getString("client_secret");

        JSONObject finalResponse = new JSONObject();
        finalResponse.put("clientSecret", clientSecret);

        return ResponseEntity.ok(finalResponse.toString());
    }
}
