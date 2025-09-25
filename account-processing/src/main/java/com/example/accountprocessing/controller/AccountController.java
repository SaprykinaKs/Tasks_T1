package com.example.accountprocessing.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "account-processing");
        response.put("message", "Account Processing Service is running");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "account-processing");
        response.put("version", "1.0.0");
        response.put("description", "Account Processing Microservice");
        response.put("endpoints", "/api/accounts/health, /api/accounts/");
        return ResponseEntity.ok(response);
    }
}