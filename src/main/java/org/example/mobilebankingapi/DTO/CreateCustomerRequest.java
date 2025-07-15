package org.example.mobilebankingapi.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCustomerRequest(
        @NotBlank(message = "Full name is required")
        String fullName,

        @Pattern(regexp = "Male|Female", message = "Gender must be Male or Female")
        String gender,

        @Email(message = "Invalid email")
        String email,

        String phoneNumber,

        String remarks,

        Integer nationalCardId,

        @Pattern(regexp = "Gold|Silver|Regular", message = "Segment must be Gold, Silver, or Regular")
        String segment

        ) {}
