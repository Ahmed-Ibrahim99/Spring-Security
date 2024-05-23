package com.amaghrabi.security.controller;

import com.amaghrabi.security.service.Iso8583Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/iso8583")
public class Iso8583Controller {
    private final Iso8583Service iso8583Service;

    public Iso8583Controller(Iso8583Service iso8583Service) {
        this.iso8583Service = iso8583Service;
    }

    @GetMapping("/get-message")
    public Map<String, Object> processMessage() {
        return iso8583Service.processIso8583Message();
    }
}
