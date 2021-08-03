package com.fizbiz.backend.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fizbiz.backend.dto.ChoosePaymentMethod;
import com.fizbiz.backend.dto.StartInvestmentDto;
import com.fizbiz.backend.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class InvestmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper;
    Investment investment;


    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        investment = new Investment();
    }

    @Test
    void startInvestment() throws Exception {

        StartInvestmentDto startInvestmentDto = new StartInvestmentDto();
        startInvestmentDto.setInvestmentType(InvestmentType.Gold);
        startInvestmentDto.setCapital(1000);
//        startInvestmentDto.setUserId("60eec28ec03c0d09ba81426b");

        this.mockMvc.perform(post("/api/investment/startInvestment")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sdXdhaEBnbWFpbC5jb20iLCJleHAiOjE2Mjc3MzAzMjJ9.6clu136EAlxxKjoFKsfU5gaGF9pRayXo8XgId2yDbt6-ZKYTmWA-Hj7zXc0m-Vwzf6iD2qyYStb0vxnpGEtpXA")
                .contentType("application/json")
                .content(mapper.writeValueAsString(startInvestmentDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void choosePaymentMethod() throws Exception {

        ChoosePaymentMethod choosePaymentMethod = new ChoosePaymentMethod();
        choosePaymentMethod.setPaymentMethod(PaymentMethod.TetherFund);
        choosePaymentMethod.setInvestmentId("60f973cbb99f1d5220ab7524");

        this.mockMvc.perform(post("/api/investment/choosePaymentMethod")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sdXdhaEBnbWFpbC5jb20iLCJleHAiOjE2Mjc3MzAzMjJ9.6clu136EAlxxKjoFKsfU5gaGF9pRayXo8XgId2yDbt6-ZKYTmWA-Hj7zXc0m-Vwzf6iD2qyYStb0vxnpGEtpXA")
                .contentType("application/json")
                .content(mapper.writeValueAsString(choosePaymentMethod)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void findInvestmentById() throws Exception {
        this.mockMvc.perform(get("/api/investment/60f8031f8e5e2f06db24be1b")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sdXdhaEBnbWFpbC5jb20iLCJleHAiOjE2Mjc3MzAzMjJ9.6clu136EAlxxKjoFKsfU5gaGF9pRayXo8XgId2yDbt6-ZKYTmWA-Hj7zXc0m-Vwzf6iD2qyYStb0vxnpGEtpXA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findAllInvestments() throws Exception {

        this.mockMvc.perform(get("/api/investment/").header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sdXdhaEBnbWFpbC5jb20iLCJleHAiOjE2Mjc3MzAzMjJ9.6clu136EAlxxKjoFKsfU5gaGF9pRayXo8XgId2yDbt6-ZKYTmWA-Hj7zXc0m-Vwzf6iD2qyYStb0vxnpGEtpXA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findAllInvestmentsOfAUser() throws Exception {
        this.mockMvc.perform(get("/api/investment/findAllInvestmentsOfAUser/60eec28ec03c0d09ba81426b")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvLmlmZW9sdXdhaEBnbWFpbC5jb20iLCJleHAiOjE2Mjc3MzAzMjJ9.6clu136EAlxxKjoFKsfU5gaGF9pRayXo8XgId2yDbt6-ZKYTmWA-Hj7zXc0m-Vwzf6iD2qyYStb0vxnpGEtpXA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}