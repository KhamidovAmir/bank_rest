package com.example.bankcards.controller;

import com.example.bankcards.dto.TransferRequestDto;
import com.example.bankcards.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransferService transferService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    void transfer_shouldReturn200() throws Exception {
        TransferRequestDto dto =
                new TransferRequestDto(1L, 2L, BigDecimal.valueOf(100));

        mockMvc.perform(post("/transfers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(transferService).transfer(any(), any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void transfer_shouldReturn403_ifNotUser() throws Exception {
        mockMvc.perform(post("/transfers")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isForbidden());
    }
}
