package com.example.demo.buy.controller;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.buy.service.BuyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BuyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyService mockBuyService;

    @Test
    void buyPlushyById_returns200() throws Exception {
        List<BuyRequestBody> buyRequestBodyList = List.of();

        mockMvc.perform(
                post("/api/v1/plushy/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(buyRequestBodyList)))
                .andExpect(status().isOk());
    }

    String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
