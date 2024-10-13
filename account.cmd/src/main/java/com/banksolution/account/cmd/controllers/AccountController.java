package com.banksolution.account.cmd.controllers;

import com.banksolution.account.cmd.commands.accounting.OpenAccountCommand;
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
@RequestMapping(path = "/api/v1/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping("/openAccount")
    public ResponseEntity<Object> openAccount(@RequestBody OpenAccountCommand openAccountCommand) {

        String id = GenerateUuidUtils.generateUuid().toString();
        try {
            openAccountCommand.setId(id);
            return ResponseEntity.ok(commandDispatcher.send(openAccountCommand));
        } catch (Exception e) {
            String safeErrorMessage = MessageFormat.format("Error while processing - {0}.", e.toString());
            log.error(safeErrorMessage, e);
            return new ResponseEntity<>(new ErrorResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
