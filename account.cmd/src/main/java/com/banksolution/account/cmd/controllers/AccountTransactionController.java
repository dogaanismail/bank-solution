package com.banksolution.account.cmd.controllers;

import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.account.cmd.dto.ErrorResponse;
import com.banksolution.common.utils.GenerateUuidUtils;
import com.banksolution.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping(path = "/api/v1/transaction")
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping("/createTransaction")
    public ResponseEntity<Object> transaction(@RequestBody TransactionCommand transactionCommand) {

        String id = GenerateUuidUtils.generateUuid().toString();
        transactionCommand.setId(id);

        try {
            return ResponseEntity.ok(commandDispatcher.send(transactionCommand));
        } catch (Exception e) {
            String safeErrorMessage = MessageFormat.format("Error while processing - {0}.", e.toString());
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new ErrorResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
