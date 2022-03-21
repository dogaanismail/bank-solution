package com.bankingsolution.account.cmd.controllers;

import com.bankingsolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.bankingsolution.account.cmd.commands.transaction.TransactionCommand;
import com.bankingsolution.account.cmd.dto.ErrorResponse;
import com.bankingsolution.account.cmd.dto.OpenAccountResponse;
import com.bankingsolution.account.cmd.dto.request.AccountCreateRequest;
import com.bankingsolution.account.cmd.dto.request.AccountTransactionRequest;
import com.bankingsolution.common.dto.BaseResponse;
import com.bankingsolution.common.utils.ObjectMapperUtils;
import com.bankingsolution.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/transaction")
@Slf4j
public class AccountTransactionController {

    private final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity transaction(@RequestBody AccountTransactionRequest accountTransactionRequest) {

        TransactionCommand command = ObjectMapperUtils.map(accountTransactionRequest, TransactionCommand.class);

        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            return ResponseEntity.ok(commandDispatcher.send(command));
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing - {0}.", e.toString());
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new ErrorResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
