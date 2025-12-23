package com.example.bankcards.controller;

import com.example.bankcards.dto.TransferRequestDto;
import com.example.bankcards.dto.TransferResponseDto;
import com.example.bankcards.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public TransferResponseDto transfer(@RequestBody TransferRequestDto dto,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return transferService.transfer(userDetails, dto);
    }
}
