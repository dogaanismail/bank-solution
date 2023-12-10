package com.bankingsolution.account.cmd.controllers;

import com.bankingsolution.account.cmd.commands.transaction.TransactionCommand;
import com.bankingsolution.account.cmd.dto.ErrorResponse;
import com.bankingsolution.account.cmd.dto.request.AccountTransactionRequest;
import com.bankingsolution.common.utils.GenerateUuidUtils;
import com.bankingsolution.common.utils.ObjectMapperUtils;
import com.bankingsolution.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
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

    private final Logger logger = Logger.getLogger(AccountTransactionController.class.getName());
    private final CommandDispatcher commandDispatcher;

    public AccountTransactionController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PostMapping("/createTransaction")
    public ResponseEntity transaction(@RequestBody TransactionCommand transactionCommand) {
        var id = GenerateUuidUtils.generateUuid().toString();
        transactionCommand.setId(id);

        try {
            return ResponseEntity.ok(commandDispatcher.send(transactionCommand));
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing - {0}.", e.toString());
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new ErrorResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
