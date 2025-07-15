package org.example.mobilebankingapi.DTO;

public record UpdateCustomerRequest(
        String fullName,
        String phoneNumber,
        String remarks
) {
}
