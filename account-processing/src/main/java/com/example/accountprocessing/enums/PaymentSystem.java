package com.example.accountprocessing.enums;

// Payment system enumeration for cards
public enum PaymentSystem {
    VISA("Visa"),
    MASTERCARD("Mastercard"),
    MIR("Mir"),
    AMERICAN_EXPRESS("American Express"),
    UNION_PAY("UnionPay");

    private final String displayName;

    PaymentSystem(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}