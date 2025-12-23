package com.example.bankcards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequestDto (
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email,
        @NotBlank String password
){}
