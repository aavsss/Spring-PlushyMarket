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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(PlushyController.class)
class PlushyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private PlushyService mockPlushyService;
    @Mock
    private FileServiceImpl fileService;
    private PlushyController plushyController;

    @Before
    public void init() {
        plushyController = new PlushyController(mockPlushyService, fileService);
    }

    @Test
    void getAllPlushies_returns200() throws Exception {
        List<Plushy> plushies = new ArrayList<>();
        Mockito.when(mockPlushyService.getPlushies()).thenReturn(plushies);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(status().isOk());
    }
}
