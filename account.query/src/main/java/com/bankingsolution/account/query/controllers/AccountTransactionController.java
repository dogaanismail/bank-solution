package com.bankingsolution.account.query.controllers;

import com.bankingsolution.account.query.domain.AccountTransaction;
import com.bankingsolution.account.query.queries.transaction.FindAllTransactionsByAccountIdQuery;
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
@RequestMapping(path = "/api/v1/transaction")
@Slf4j
public class AccountTransactionController {

    @Autowired
    private final QueryDispatcher queryDispatcher;

    public AccountTransactionController(QueryDispatcher queryDispatcher) {
        this.queryDispatcher = queryDispatcher;
    }

    @GetMapping(value = "/get/{accountId}", produces = "application/json")
    public ResponseEntity<AccountTransaction> getTransactionsByAccountId(@PathVariable(value = "accountId") String accountId) {

        List<AccountTransaction> transactions =
                queryDispatcher.send(new FindAllTransactionsByAccountIdQuery(accountId));

        if (transactions == null || transactions.size() == 0)
            throw new DataNotFoundException("Transactions by account Id could not be found!");

        return new ResponseEntity<>(transactions.get(0), HttpStatus.OK);
    }
}
