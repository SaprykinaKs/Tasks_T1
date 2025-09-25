package com.example.accountprocessing.enums;

// Card status enumeration
public enum CardStatus {
    ACTIVE("Active"),
    BLOCKED("Blocked"),
    EXPIRED("Expired"),
    CANCELLED("Cancelled");

    private final String displayName;

    CardStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}