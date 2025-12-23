package com.example.bankcards.controller;

import com.example.bankcards.dto.UserResponseDto;
import com.example.bankcards.entity.AccountStatus;
import com.example.bankcards.entity.Role;
import com.example.bankcards.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUser_shouldReturn200() throws Exception {
        UserResponseDto dto = new UserResponseDto(
                1L, "A", "B", "a@mail.com", Role.USER, AccountStatus.ACTIVE
        );

        when(userService.getUserById(1L)).thenReturn(dto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("a@mail.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void blockUser_shouldReturn200() throws Exception {
        when(userService.blockUser(1L))
                .thenReturn(mock(UserResponseDto.class));

        mockMvc.perform(put("/users/1/block"))
                .andExpect(status().isOk());

        verify(userService).blockUser(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void activateUser_shouldReturn200() throws Exception {
        when(userService.activateUser(1L))
                .thenReturn(mock(UserResponseDto.class));

        mockMvc.perform(put("/users/1/activate"))
                .andExpect(status().isOk());

        verify(userService).activateUser(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService).deleteUser(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getUser_shouldReturn403_ifNotAdmin() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isForbidden());
    }
}

