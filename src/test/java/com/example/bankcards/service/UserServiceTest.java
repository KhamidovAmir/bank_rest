package com.example.bankcards.service;

import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.entity.AccountStatus;
import com.example.bankcards.entity.User;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void blockUser_shouldChangeStatus() {
        User user = new User();
        user.setId(1L);
        user.setAccountStatus(AccountStatus.ACTIVE);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponseDto dto = userService.blockUser(1L);

        assertEquals(AccountStatus.BLOCKED, dto.accountStatus());
    }
}
