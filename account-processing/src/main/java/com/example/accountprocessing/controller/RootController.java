package com.example.accountprocessing.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "account-processing");
        response.put("version", "1.0.0");
        response.put("status", "UP");
        response.put("description", "Account Processing Microservice - Banking System");
        response.put("port", 8082);
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("health", "/api/accounts/health");
        endpoints.put("accounts", "/api/accounts/");
        
        response.put("available_endpoints", endpoints);
        return ResponseEntity.ok(response);
    }
}