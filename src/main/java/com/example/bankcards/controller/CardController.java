package com.example.bankcards.controller;

import com.example.bankcards.dto.CardResponseDto;
import com.example.bankcards.dto.CreateCardRequestDto;
import com.example.bankcards.service.CardService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CardResponseDto createCard(@RequestBody CreateCardRequestDto dto) {
        return cardService.createCard(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{cardId}")
    public void deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
    }

    @GetMapping("/{cardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<CardResponseDto> getUserCards(@PathVariable Long cardId,
                                              @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
                                              @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(100) int size ) {
        return cardService.getUserCards(cardId, page, size);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public Page<CardResponseDto> getMyCards(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
                                            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
                                            @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(100) int size ) {
        return cardService.getMyCards(userDetails, page, size);
    }
}
