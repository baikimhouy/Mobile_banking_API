package org.example.mobilebankingapi.DTO;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record AccountResponse(
        Integer id,
        String accountNumber,
        BigDecimal balance,
        String currency,
        boolean active,
        Integer customerId,
        String customerName,
         BigDecimal overLimit
) {
}
