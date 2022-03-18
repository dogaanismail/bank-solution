package com.bankingsolution.account.cmd;

import com.bankingsolution.account.cmd.commands.CommandHandler;
import com.bankingsolution.account.cmd.commands.OpenAccountCommand;
import com.bankingsolution.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandApplication {

    @Autowired
    private final CommandDispatcher commandDispatcher;

    @Autowired
    private final CommandHandler commandHandler;

    public CommandApplication(CommandDispatcher commandDispatcher,
                              CommandHandler commandHandler) {
        this.commandDispatcher = commandDispatcher;
        this.commandHandler = commandHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
    }
}
