package com.example.clientprocessing.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "client-processing");
        response.put("message", "Client Processing Service is running");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "client-processing");
        response.put("version", "1.0.0");
        response.put("description", "Client Processing Microservice");
        response.put("endpoints", "/api/clients/health, /api/clients/");
        return ResponseEntity.ok(response);
    }
}