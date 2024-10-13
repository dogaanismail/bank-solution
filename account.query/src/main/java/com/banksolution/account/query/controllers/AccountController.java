package com.banksolution.account.query.controllers;

import com.banksolution.account.query.queries.accounting.FindAccountByIdQuery;
import com.banksolution.account.query.queries.accounting.FindAllAccountsQuery;
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
@RequestMapping(path = "/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final QueryDispatcher queryDispatcher;

    @GetMapping(value = "/getAccounts", produces = "application/json")
    public ResponseEntity<ResponseModel> getAllAccounts() {

        try {
            ResponseModel response = queryDispatcher.send(new FindAllAccountsQuery());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/get/{accountId}", produces = "application/json")
    public ResponseEntity<ResponseModel> getAccountById(
            @PathVariable(value = "accountId") String accountId) {

        try {
            ResponseModel response = queryDispatcher.send(new FindAccountByIdQuery(accountId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
