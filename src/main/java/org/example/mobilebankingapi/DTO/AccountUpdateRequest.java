package org.example.mobilebankingapi.DTO;

import java.math.BigDecimal;

public record AccountUpdateRequest(
        BigDecimal balance,
        String currency
) {
}
