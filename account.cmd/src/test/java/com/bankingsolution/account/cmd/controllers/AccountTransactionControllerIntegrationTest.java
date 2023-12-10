package com.bankingsolution.account.cmd.controllers;

import com.bankingsolution.account.cmd.dto.request.AccountTransactionRequest;
import com.bankingsolution.common.enums.TransactionDirection;
import com.bankingsolution.cqrs.core.generics.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountTransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldBeCompletedTransactionSuccessfully() throws Exception {
        final var accountTransactionRequest = new AccountTransactionRequest(
                "EXAMPLE_ACCOUNT_1",
                BigDecimal.valueOf(100),
                "EUR",
                TransactionDirection.IN,
                "Rest API Integration Test"
        );

        mvc.perform(post("/api/v1/transaction/createTransaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransactionRequest))
                )
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.toString()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnErrorIfCurrencyIsInvalid() throws Exception {
        final var accountTransactionRequest = new AccountTransactionRequest(
                "EXAMPLE_ACCOUNT_1",
                BigDecimal.valueOf(100),
                "TR",
                TransactionDirection.IN,
                "Rest API Integration Test"
        );

        mvc.perform(post("/api/v1/transaction/createTransaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransactionRequest))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnErrorIfAmountIsInvalid() throws Exception {
        final var accountTransactionRequest = new AccountTransactionRequest(
                "EXAMPLE_ACCOUNT_1",
                BigDecimal.valueOf(-900),
                "EUR",
                TransactionDirection.OUT,
                "Rest API Integration Test"
        );

        mvc.perform(post("/api/v1/transaction/createTransaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransactionRequest))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnErrorIfDescriptionIsMissing() throws Exception {
        final var accountTransactionRequest = new AccountTransactionRequest(
                "EXAMPLE_ACCOUNT_1",
                BigDecimal.valueOf(10),
                "EUR",
                TransactionDirection.OUT,
                ""
        );

        mvc.perform(post("/api/v1/transaction/createTransaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransactionRequest))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
