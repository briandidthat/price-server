package com.briandidthat.financialserver.controller;

import com.briandidthat.financialserver.domain.fred.FredSeriesId;
import com.briandidthat.financialserver.service.FredService;
import com.briandidthat.financialserver.util.TestingConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FredController.class)
class FredControllerTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FredService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getObservations() throws Exception {
        String outputJson = mapper.writeValueAsString(TestingConstants.MORTGAGE_RATE_RESPONSE);

        when(service.getObservations(FredSeriesId.AVERAGE_MORTGAGE_RATE, new LinkedHashMap<>())).thenReturn(TestingConstants.MORTGAGE_RATE_RESPONSE);

        this.mockMvc.perform(get("/fred/observations/{operation}","averageMortgageRate"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson))
                .andDo(print());
    }

    // 400 error
    @Test
    void getObservationsShouldHandleOperationNotFound() throws Exception {
        this.mockMvc.perform(get("/fred/observations/{operation}","randomOperation"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid series id. Available operations:")))
                .andDo(print());
    }
}