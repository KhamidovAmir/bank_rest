package com.example.bankcards.controller;

import com.example.bankcards.dto.JwtResponseDto;
import com.example.bankcards.dto.LoginRequestDto;
import com.example.bankcards.dto.RegisterUserRequestDto;
import com.example.bankcards.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_shouldReturn201() throws Exception {
        RegisterUserRequestDto dto =
                new RegisterUserRequestDto("a@mail.com", "1234", "A", "B");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        Mockito.verify(authService).register(dto);
    }

    @Test
    void login_shouldReturn200() throws Exception {
        LoginRequestDto dto = new LoginRequestDto("a@mail.com", "1234");

        Mockito.when(authService.login(dto))
                .thenReturn(new JwtResponseDto("token"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
