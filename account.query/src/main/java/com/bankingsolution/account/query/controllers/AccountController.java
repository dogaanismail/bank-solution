package com.bankingsolution.account.query.controllers;

import com.bankingsolution.account.query.domain.Account;
import com.bankingsolution.account.query.queries.accounting.FindAccountByIdQuery;
import com.bankingsolution.account.query.queries.accounting.FindAllAccountsQuery;
import com.bankingsolution.common.exceptions.DataNotFoundException;
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

    @Autowired
    private final QueryDispatcher queryDispatcher;

    public AccountController(QueryDispatcher queryDispatcher) {
        this.queryDispatcher = queryDispatcher;
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = queryDispatcher.send(new FindAllAccountsQuery());

        if (accounts == null || accounts.size() == 0)
            throw new DataNotFoundException("There is no bank account!");

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{accountId}", produces = "application/json")
    public ResponseEntity<Account> getAccountById(@PathVariable(value = "accountId") String accountId) {

        List<Account> accounts = queryDispatcher.send(new FindAccountByIdQuery(accountId));

        if (accounts == null || accounts.size() == 0)
            throw new DataNotFoundException("Bank Account with 'id' %s was not found!");

        return new ResponseEntity<>(accounts.get(0), HttpStatus.OK);
    }
}
