package com.example.clientprocessing.enums;

// Product key enumeration for banking products
public enum ProductKey {
    DC("Debit Card"),
    CC("Credit Card"),
    AC("Account"),
    IPO("Investment Product Offer"),
    PC("Personal Credit"),
    PENS("Pension"),
    NS("Savings Account"),
    INS("Insurance"),
    BS("Banking Service");

    private final String displayName;

    ProductKey(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}