package com.bankingsolution.account.cmd.controllers;

import com.bankingsolution.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/account")
@Slf4j
public class AccountTransactionController {

    private final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;


}
