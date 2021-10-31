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

        Investment investment = new Investment();
        investment.setCapital(5000.0);
        investment.setPaymentMethod(PaymentMethod.TetherFund);
        investment.setUserId(2L);

        this.mockMvc.perform(post("/api/investment/startInvestment")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiamF5emVlQGdtYWlsLmNvbSIsImV4cCI6MTYzNjUzNTA0N30.8K-pj13ZmfIy-aWftj_F-mbT0QU3gW9PtJ7ljr9qJSdTdAkovIf0zg0YFUb79sxhLAvMHxYU-SULjgBf4scl-g\"")
                .contentType("application/json")
                .content(mapper.writeValueAsString(investment)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }


    @Test
    void findInvestmentById() throws Exception {
        this.mockMvc.perform(get("/api/investment/1")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiamF5emVlQGdtYWlsLmNvbSIsImV4cCI6MTYzNjUzNTA0N30.8K-pj13ZmfIy-aWftj_F-mbT0QU3gW9PtJ7ljr9qJSdTdAkovIf0zg0YFUb79sxhLAvMHxYU-SULjgBf4scl-g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findUserInvestmentSummary() throws Exception {
        this.mockMvc.perform(get("/api/investment/getUserInvestmentSummary/2")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiamF5emVlQGdtYWlsLmNvbSIsImV4cCI6MTYzNjUzNTA0N30.8K-pj13ZmfIy-aWftj_F-mbT0QU3gW9PtJ7ljr9qJSdTdAkovIf0zg0YFUb79sxhLAvMHxYU-SULjgBf4scl-g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void changeInvestmentStatus() throws Exception {
        this.mockMvc.perform(get("/api/investment/status/8")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiamF5emVlQGdtYWlsLmNvbSIsImV4cCI6MTYzNjUzNTA0N30.8K-pj13ZmfIy-aWftj_F-mbT0QU3gW9PtJ7ljr9qJSdTdAkovIf0zg0YFUb79sxhLAvMHxYU-SULjgBf4scl-g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findAllInvestments() throws Exception {

        this.mockMvc.perform(get("/api/investment/").header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiamF5emVlQGdtYWlsLmNvbSIsImV4cCI6MTYzNjUzNTA0N30.8K-pj13ZmfIy-aWftj_F-mbT0QU3gW9PtJ7ljr9qJSdTdAkovIf0zg0YFUb79sxhLAvMHxYU-SULjgBf4scl-g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findAllInvestmentsOfAUser() throws Exception {
        this.mockMvc.perform(get("/api/investment/findAllInvestmentsOfAUser/3")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiamF5emVlQGdtYWlsLmNvbSIsImV4cCI6MTYzNjUzNTA0N30.8K-pj13ZmfIy-aWftj_F-mbT0QU3gW9PtJ7ljr9qJSdTdAkovIf0zg0YFUb79sxhLAvMHxYU-SULjgBf4scl-g"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}