package com.example.bankcards.controller;

import com.example.bankcards.dto.CardResponseDto;
import com.example.bankcards.dto.CreateCardRequestDto;
import com.example.bankcards.service.CardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Test
    void createCard_shouldReturn200() throws Exception {
        CreateCardRequestDto dto = new CreateCardRequestDto(1L, null, null);

        Mockito.when(cardService.createCard(Mockito.any()))
                .thenReturn(Mockito.mock(CardResponseDto.class));

        mockMvc.perform(post("/cards")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk());
    }
}
