package com.example.clientprocessing.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

// Validator for ClientId format: XXFFNNNNNNNN

public class ClientIdFormatValidator implements ConstraintValidator<ClientIdFormat, String> {

    // Pattern: exactly 12 digits (XX FF NNNNNNNN)
    private static final Pattern CLIENT_ID_PATTERN = Pattern.compile("^\\d{12}$");

    @Override
    public void initialize(ClientIdFormat constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String clientId, ConstraintValidatorContext context) {
        if (clientId == null) {
            return false;
        }
        
        // Check if it matches the pattern (12 digits)
        if (!CLIENT_ID_PATTERN.matcher(clientId).matches()) {
            return false;
        }
        
        return true;
    }
}