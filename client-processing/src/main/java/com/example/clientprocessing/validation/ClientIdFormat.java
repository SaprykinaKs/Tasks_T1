package com.example.clientprocessing.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation for ClientId format: XXFFNNNNNNNN
 * XX - region number (2 digits)
 * FF - bank branch number (2 digits) 
 * NNNNNNNN - ordinal number (8 digits)
 * Example: 770100000001, 770200000023
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClientIdFormatValidator.class)
@Documented
public @interface ClientIdFormat {
    String message() default "ClientId must follow format XXFFNNNNNNNN (e.g., 770100000001)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}