package org.example.mobilebankingapi.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AccountRequest(
        String accountNumber,
        @NotNull BigDecimal balance,
        @NotBlank String currency,
        @NotNull @Positive Integer customerId
) {}