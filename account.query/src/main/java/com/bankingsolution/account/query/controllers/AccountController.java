package com.bankingsolution.account.query.controllers;

import com.bankingsolution.account.query.domain.Account;
import com.bankingsolution.account.query.queries.FindAccountByIdQuery;
import com.bankingsolution.account.query.queries.FindAllAccountsQuery;
import com.bankingsolution.common.exceptions.DataNotFoundException;
import com.bankingsolution.cqrs.core.infrastructure.QueryDispatcher;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = queryDispatcher.send(new FindAllAccountsQuery());
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") String id) {
        List<Account> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
        if (accounts == null || accounts.size() == 0) {
            throw new DataNotFoundException("Bank Account with 'id' %s was not found".formatted(id));
        }
        return new ResponseEntity<>(accounts.get(0), HttpStatus.OK);
    }
}
