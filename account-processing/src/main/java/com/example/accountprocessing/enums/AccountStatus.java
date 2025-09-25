package com.example.accountprocessing.enums;

//Account status enumeration
    
public enum AccountStatus {
    ACTIVE("Active"),
    CLOSED("Closed"),
    BLOCKED("Blocked"),
    SUSPENDED("Suspended");

    private final String displayName;

    AccountStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}