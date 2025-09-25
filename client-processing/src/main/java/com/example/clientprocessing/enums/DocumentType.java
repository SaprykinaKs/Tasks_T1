package com.example.clientprocessing.enums;

// Document type enumeration for client identification documents
public enum DocumentType {
    PASSPORT("Passport"),
    INT_PASSPORT("International Passport"), 
    BIRTH_CERT("Birth Certificate");

    private final String displayName;

    DocumentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}