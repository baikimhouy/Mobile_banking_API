package org.example.mobilebankingapi.DTO;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String fullName,
        String gender,
        String phoneNumber,
        String email,
        String remarks,
        Integer nationalCardId,
        String segment,
        boolean verified
) {}

