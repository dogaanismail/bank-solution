package com.bankingsolution.account.cmd.controllers;

import com.bankingsolution.account.cmd.dto.request.AccountCreateRequest;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldCreateNewAccountSuccessfully() throws Exception {
        AccountCreateRequest accountCreateRequest = new AccountCreateRequest();

        accountCreateRequest.setCustomerId(1);
        accountCreateRequest.setCountry("ESTONIA");
        accountCreateRequest.setCurrencies(List.of(new String[]{"SEK", "USD"}));

        this.mvc.perform(post("/api/v1/account/openAccount")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountCreateRequest))
                )
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.toString()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldNotCreateAccountIfCurrencyInvalid() throws Exception {
        AccountCreateRequest accountCreateRequest = new AccountCreateRequest();

        accountCreateRequest.setCustomerId(1);
        accountCreateRequest.setCountry("ESTONIA");
        accountCreateRequest.setCurrencies(List.of(new String[]{"TRY"}));

        this.mvc.perform(post("/api/v1/account/openAccount")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountCreateRequest))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldNotCreateAccountIfAlreadyExists() throws Exception {
        AccountCreateRequest accountCreateRequest = new AccountCreateRequest();

        accountCreateRequest.setCustomerId(1);
        accountCreateRequest.setCountry("ESTONIA");
        accountCreateRequest.setCurrencies(List.of(new String[]{"SEK", "USD"}));

        this.mvc.perform(post("/api/v1/account/openAccount")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountCreateRequest))
                )
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.toString()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        AccountCreateRequest accountCreateRequest_2 = new AccountCreateRequest();

        accountCreateRequest_2.setCustomerId(1);
        accountCreateRequest_2.setCountry("ESTONIA");
        accountCreateRequest_2.setCurrencies(List.of(new String[]{"USD"}));

        this.mvc.perform(post("/api/v1/account/openAccount")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountCreateRequest_2))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldNotCreateIfCustomerDoesNotExist() throws Exception {
        AccountCreateRequest accountCreateRequest = new AccountCreateRequest();

        accountCreateRequest.setCustomerId(999999999);
        accountCreateRequest.setCountry("ESTONIA");
        accountCreateRequest.setCurrencies(List.of(new String[]{"TRY"}));

        this.mvc.perform(post("/api/v1/account/openAccount")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountCreateRequest))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
