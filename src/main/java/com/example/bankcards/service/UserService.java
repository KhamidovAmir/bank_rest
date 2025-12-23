package com.example.bankcards.service;

import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.entity.AccountStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<UserResponseDto> getAllUsers(int page, int size) {
        return userRepository
                .findAll(PageRequest.of(page, size))
                .map(this::toDto);
    }

    public UserResponseDto getUserById(Long id) {
        return toDto(
                userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found"))
        );
    }

    @Transactional
    public UserResponseDto blockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.BLOCKED);
        return toDto(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto activateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.ACTIVE);
        return toDto(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getAccountStatus()
        );
    }

}
