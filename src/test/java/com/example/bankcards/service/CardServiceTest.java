package com.example.bankcards.service;

import com.example.bankcards.dto.CardResponseDto;
import com.example.bankcards.dto.CreateCardRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardService cardService;

    @Test
    void createCard_shouldCreateCard() {
        CreateCardRequestDto dto =
                new CreateCardRequestDto(1L, null, null);

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        CardResponseDto response = cardService.createCard(dto);

        assertNotNull(response);
        verify(cardRepository).save(any(Card.class));
    }

    @Test
    void deleteCard_shouldThrowIfNotExists() {
        when(cardRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(NotFoundException.class,
                () -> cardService.deleteCard(1L));
    }
}
