package com.example.creditprocessing.dto;

public record CreditDecisionMessage(Long clientId, String productCode, String status, String reason) {
}
