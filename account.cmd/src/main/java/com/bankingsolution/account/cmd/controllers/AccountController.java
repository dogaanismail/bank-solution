package com.bankingsolution.account.cmd.controllers;

import com.bankingsolution.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/account")
@Slf4j
public class AccountController {
    //  private final CommandDispatcher commandDispatcher;

    //  public AccountController(CommandDispatcher commandDispatcher) {
    //  this.commandDispatcher = commandDispatcher;
    //  }

    //    @PostMapping
     //  public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
    //  var id = UUID.randomUUID().toString();
    //  command.setId(id);
    //  commandDispatcher.send(command);
    //  return new ResponseEntity<>(
    //  new OpenAccountResponse("Bank account creation request completed successfully", id),
    //  HttpStatus.CREATED);
    //  }
}
