package com.bankingsolution.account.cmd;

import com.bankingsolution.account.cmd.commands.accounting.IAccountCommandHandler;
import com.bankingsolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.bankingsolution.account.cmd.commands.transaction.ITransactionCommandHandler;
import com.bankingsolution.account.cmd.commands.transaction.TransactionCommand;
import com.bankingsolution.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;

@EnableRabbit
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class CommandApplication {

    @Autowired
    private final CommandDispatcher commandDispatcher;

    @Autowired
    private final IAccountCommandHandler accountCommandHandler;

    @Autowired
    private final ITransactionCommandHandler transactionCommandHandler;

    public CommandApplication(CommandDispatcher commandDispatcher,
                              IAccountCommandHandler accountCommandHandler,
                              ITransactionCommandHandler transactionCommandHandler) {
        this.commandDispatcher = commandDispatcher;
        this.accountCommandHandler = accountCommandHandler;
        this.transactionCommandHandler = transactionCommandHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(OpenAccountCommand.class, accountCommandHandler::handle);
        commandDispatcher.registerHandler(TransactionCommand.class, transactionCommandHandler::handle);
    }
}
