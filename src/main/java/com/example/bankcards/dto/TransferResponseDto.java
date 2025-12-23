package com.example.bankcards.dto;

import com.example.bankcards.entity.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponseDto (
        Long fromCardId,
        Long toCardId,
        BigDecimal amount,
        TransferStatus transferStatus,
        String failureReason,
        LocalDateTime createdAt
)
{}
