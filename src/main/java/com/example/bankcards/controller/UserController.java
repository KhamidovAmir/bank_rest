package com.example.bankcards.controller;

import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.service.UserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponseDto> getAllUsers(@RequestParam(defaultValue = "0") @Min(0) int page,
                                             @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @PutMapping("/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDto blockUser(@PathVariable Long id) {
        return userService.blockUser(id);
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDto activateUser(@PathVariable Long id) {
        return userService.activateUser(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
