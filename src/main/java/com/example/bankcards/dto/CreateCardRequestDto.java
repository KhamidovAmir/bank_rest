package com.example.bankcards.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCardRequestDto (
        @NotNull Long ownerId,
        @NotNull BigDecimal balance,
        @NotNull LocalDate expirationDate
        )
{}
