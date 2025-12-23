package com.example.bankcards.controller;

import com.example.bankcards.dto.JwtResponseDto;
import com.example.bankcards.dto.LoginRequestDto;
import com.example.bankcards.dto.RegisterUserRequestDto;
import com.example.bankcards.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid RegisterUserRequestDto dto){
        authService.register(dto);

    }

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody @Valid LoginRequestDto dto){
        return authService.login(dto);
    }
}
