package com.example.bankcards.service;

import com.example.bankcards.dto.TransferRequestDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.BadRequestException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    void transfer_shouldFailIfAmountNegative() {
        TransferRequestDto dto =
                new TransferRequestDto(1L, 2L, BigDecimal.valueOf(-10));

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("a@mail.com");

        User user = new User();
        when(userRepository.findByEmail("a@mail.com"))
                .thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class,
                () -> transferService.transfer(userDetails, dto));
    }
}
