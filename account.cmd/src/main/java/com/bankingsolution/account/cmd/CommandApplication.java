package com.bankingsolution.account.cmd;

import com.bankingsolution.account.cmd.commands.accounting.IAccountCommandHandler;
import com.bankingsolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.bankingsolution.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@EnableRabbit
@SpringBootApplication
public class CommandApplication {

    @Autowired
    private final CommandDispatcher commandDispatcher;

    @Autowired
    private final IAccountCommandHandler IAccountCommandHandler;

    public CommandApplication(CommandDispatcher commandDispatcher,
                              IAccountCommandHandler IAccountCommandHandler) {
        this.commandDispatcher = commandDispatcher;
        this.IAccountCommandHandler = IAccountCommandHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(OpenAccountCommand.class, IAccountCommandHandler::handle);
    }
}
