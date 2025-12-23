package com.example.bankcards.dto;

import com.example.bankcards.entity.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardResponseDto (
        Long id,
        String maskedNumber,
        LocalDate expirationDate,
        CardStatus status,
        BigDecimal balance
)
{}
