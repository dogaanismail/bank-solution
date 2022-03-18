package com.bankingsolution.account.cmd.controllers;

import com.bankingsolution.account.cmd.commands.OpenAccountCommand;
import com.bankingsolution.account.cmd.dto.OpenAccountResponse;
import com.bankingsolution.common.dto.BaseResponse;
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
@RequestMapping(path = "/api/v1/account")
@Slf4j
public class AccountController {
    private final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("Bank account creation request " +
                    "completed successfully!", id), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0},", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request to open a new bank " +
                    "account for id - {0}.", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
