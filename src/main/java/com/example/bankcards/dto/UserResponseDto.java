package com.example.bankcards.dto;

import com.example.bankcards.entity.AccountStatus;
import com.example.bankcards.entity.Role;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role,
        AccountStatus accountStatus
) {}
