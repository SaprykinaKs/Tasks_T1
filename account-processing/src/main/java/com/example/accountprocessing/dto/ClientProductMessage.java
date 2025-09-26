package com.example.accountprocessing.dto;

import java.math.BigDecimal;

public record ClientProductMessage(Long clientId, String productCode, BigDecimal amount, Integer monthCount) {
}
