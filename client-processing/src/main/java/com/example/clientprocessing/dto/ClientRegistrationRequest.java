package com.example.clientprocessing.dto;

import com.example.clientprocessing.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record ClientRegistrationRequest(
    @NotBlank String fullName,
    @NotBlank String documentNumber,
    @NotNull DocumentType documentType,
    @NotNull @Past LocalDate dateOfBirth,
    @NotBlank String email,
    boolean blacklistCheck
) {
}
