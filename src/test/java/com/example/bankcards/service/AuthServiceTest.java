package com.example.bankcards.service;

import com.example.bankcards.dto.RegisterUserRequestDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ConflictException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldSaveUser() {
        RegisterUserRequestDto dto =
                new RegisterUserRequestDto("a@mail.com", "123", "A", "B");

        when(userRepository.existsByEmail(dto.email()))
                .thenReturn(false);
        when(passwordEncoder.encode(dto.password()))
                .thenReturn("encoded");

        authService.register(dto);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_shouldThrowIfEmailExists() {
        RegisterUserRequestDto dto =
                new RegisterUserRequestDto("a@mail.com", "123", "A", "B");

        when(userRepository.existsByEmail(dto.email()))
                .thenReturn(true);

        assertThrows(ConflictException.class,
                () -> authService.register(dto));
    }
}
