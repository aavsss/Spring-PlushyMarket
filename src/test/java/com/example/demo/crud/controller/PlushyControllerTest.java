package com.example.demo.crud.controller;

import com.example.demo.crud.model.Plushy;
import com.example.demo.crud.service.PlushyService;
import com.example.demo.globalService.FileService.FileServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class PlushyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlushyService mockPlushyService;
    @Mock
    private FileServiceImpl mockFileService;

    @Test
    void getAllPlushies_returns200() throws Exception {
        List<Plushy> plushies = new ArrayList<>();
        Mockito.when(mockPlushyService.getPlushies()).thenReturn(plushies);
        mockMvc.perform(
                get("/api/v1/plushy"))
                .andExpect(status().isOk());
    }

    @Test
    void getPlushyById_returns200() throws Exception {
        Plushy plushy = new Plushy();
        Mockito.when(mockPlushyService.getPlushyById(any())).thenReturn(plushy);
        mockMvc.perform(
                get("/api/v1/plushy/{plushyId}", 1))
                .andExpect(status().isOk());

    }

    @Test
    void addPlushy_returns200() throws Exception {
        Plushy plushy = new Plushy();
        mockMvc.perform(
                post("/api/v1/plushy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(plushy)))
                .andExpect(status().isOk());
    }

    @Test
    void findByName_returns200() throws Exception {
        String fileName = "fileName";
        Mockito.when(mockFileService.findByName(any())).thenReturn(fileName);
        mockMvc.perform(
                get("/api/v1/plushy/pic"))
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