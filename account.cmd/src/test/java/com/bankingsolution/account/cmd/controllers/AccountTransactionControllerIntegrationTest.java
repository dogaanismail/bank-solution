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
    public void shouldBeCompletedTransactionSuccesfully() throws Exception {
        AccountTransactionRequest accountTransactionRequest = new AccountTransactionRequest();

        accountTransactionRequest.setAccountId("EXAMPLE_ACCOUNT_1");
        accountTransactionRequest.setAmount(BigDecimal.valueOf(100));
        accountTransactionRequest.setCurrency("EUR");
        accountTransactionRequest.setDirection(TransactionDirection.IN.toString());
        accountTransactionRequest.setDescription("Rest API Integration Test");

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
        AccountTransactionRequest accountTransactionRequest = new AccountTransactionRequest();

        accountTransactionRequest.setAccountId("EXAMPLE_ACCOUNT_1");
        accountTransactionRequest.setAmount(BigDecimal.valueOf(100));
        accountTransactionRequest.setCurrency("TR");
        accountTransactionRequest.setDirection(TransactionDirection.IN.toString());
        accountTransactionRequest.setDescription("Rest API Integration Test");

        mvc.perform(post("/api/v1/transaction/createTransaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransactionRequest))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnErrorIfDirectionIsInvalid() throws Exception {
        AccountTransactionRequest accountTransactionRequest = new AccountTransactionRequest();

        accountTransactionRequest.setAccountId("EXAMPLE_ACCOUNT_1");
        accountTransactionRequest.setAmount(BigDecimal.valueOf(100));
        accountTransactionRequest.setCurrency("EUR");
        accountTransactionRequest.setDirection("OUTTT");
        accountTransactionRequest.setDescription("Rest API Integration Test");

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
        AccountTransactionRequest accountTransactionRequest = new AccountTransactionRequest();

        accountTransactionRequest.setAccountId("EXAMPLE_ACCOUNT_1");
        accountTransactionRequest.setAmount(BigDecimal.valueOf(-900));
        accountTransactionRequest.setCurrency("EUR");
        accountTransactionRequest.setDirection(TransactionDirection.OUT.toString());
        accountTransactionRequest.setDescription("Rest API Integration Test");

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
        AccountTransactionRequest accountTransactionRequest = new AccountTransactionRequest();

        accountTransactionRequest.setAccountId("EXAMPLE_ACCOUNT_1");
        accountTransactionRequest.setAmount(BigDecimal.valueOf(-900));
        accountTransactionRequest.setCurrency("EUR");
        accountTransactionRequest.setDirection(TransactionDirection.OUT.toString());
        accountTransactionRequest.setDescription("");

        mvc.perform(post("/api/v1/transaction/createTransaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransactionRequest))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnErrorIfThereIsInsufficentFunds() throws Exception {
        AccountTransactionRequest accountTransactionRequest = new AccountTransactionRequest();

        accountTransactionRequest.setAccountId("EXAMPLE_ACCOUNT_1");
        accountTransactionRequest.setAmount(BigDecimal.valueOf(500)); //suppose that current balance is 200
        accountTransactionRequest.setCurrency("EUR");
        accountTransactionRequest.setDirection(TransactionDirection.OUT.toString());
        accountTransactionRequest.setDescription("Withdraw");

        mvc.perform(post("/api/v1/transaction/createTransaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTransactionRequest))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
