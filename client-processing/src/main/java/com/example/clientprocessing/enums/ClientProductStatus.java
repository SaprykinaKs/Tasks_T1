package com.example.clientprocessing.enums;

// Client product status enumeration
public enum ClientProductStatus {
    ACTIVE("Active"),
    CLOSED("Closed"),
    BLOCKED("Blocked"),
    ARRESTED("Arrested");

    private final String displayName;

    ClientProductStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}