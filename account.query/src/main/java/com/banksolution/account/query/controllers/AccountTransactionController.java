package com.banksolution.account.query.controllers;

import com.banksolution.account.query.queries.transaction.FindAllTransactionsByAccountIdQuery;
import com.banksolution.cqrs.core.generics.ResponseModel;
import com.banksolution.cqrs.core.infrastructure.QueryDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/transaction")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final QueryDispatcher queryDispatcher;

    @GetMapping(value = "/getTransactions/{accountId}", produces = "application/json")
    public ResponseEntity<ResponseModel> getTransactionsByAccountId(
            @PathVariable(value = "accountId") String accountId) {
        
        try {
            ResponseModel response = queryDispatcher.send(new FindAllTransactionsByAccountIdQuery(accountId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
