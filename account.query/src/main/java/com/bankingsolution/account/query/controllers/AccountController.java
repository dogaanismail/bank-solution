package com.bankingsolution.account.query.controllers;

import com.bankingsolution.account.query.domain.Account;
import com.bankingsolution.account.query.queries.accounting.FindAccountByIdQuery;
import com.bankingsolution.account.query.queries.accounting.FindAllAccountsQuery;
import com.bankingsolution.common.exceptions.DataNotFoundException;
import com.bankingsolution.cqrs.core.generics.ResponseModel;
import com.bankingsolution.cqrs.core.infrastructure.QueryDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/account")
@Slf4j
public class AccountController {
    private final QueryDispatcher queryDispatcher;

    public AccountController(QueryDispatcher queryDispatcher) {
        this.queryDispatcher = queryDispatcher;
    }

    @GetMapping(value = "/getAccounts", produces = "application/json")
    public ResponseEntity getAllAccounts() {
        try {
            ResponseModel response = queryDispatcher.send(new FindAllAccountsQuery());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/get/{accountId}", produces = "application/json")
    public ResponseEntity getAccountById(@PathVariable(value = "accountId") String accountId) {
        try {
            ResponseModel response = queryDispatcher.send(new FindAccountByIdQuery(accountId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
