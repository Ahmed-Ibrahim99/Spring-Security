package com.amaghrabi.security.controller;

import com.amaghrabi.security.model.FawryRequest;
import com.amaghrabi.security.service.FawryService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/fawry-payment")
public class FawryController {
    private final FawryService fawryService;

    public FawryController(FawryService fawryService) {
        this.fawryService = fawryService;
    }

    @PostMapping("/generate-signature")
    public String generateSignature(@RequestBody FawryRequest request) {
        try {
            return this.fawryService.generateSignature(request);
        } catch (NoSuchAlgorithmException e) {
            return "Error generating signature: " + e.getMessage();
        }
    }
}
