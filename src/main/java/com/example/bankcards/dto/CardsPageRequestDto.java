package com.example.bankcards.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CardsPageRequestDto (
        @NotNull @Min(0) int page,
        @NotNull @Min(1) int size
)
{ }
