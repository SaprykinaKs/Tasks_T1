package com.example.creditprocessing.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "credit-processing");
        response.put("message", "Credit Processing Service is running");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "credit-processing");
        response.put("version", "1.0.0");
        response.put("description", "Credit Processing Microservice");
        response.put("endpoints", "/api/credits/health, /api/credits/");
        return ResponseEntity.ok(response);
    }
}