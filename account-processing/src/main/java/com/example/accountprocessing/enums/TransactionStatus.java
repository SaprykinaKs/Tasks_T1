package com.example.accountprocessing.enums;

//Transaction status enumeration
public enum TransactionStatus {
    ALLOWED("Allowed"),
    PROCESSING("Processing"),
    COMPLETE("Complete"),
    BLOCKED("Blocked"),
    CANCELLED("Cancelled");

    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}