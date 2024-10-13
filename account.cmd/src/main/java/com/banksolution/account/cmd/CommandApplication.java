package com.banksolution.account.cmd;

import com.banksolution.account.cmd.commands.accounting.IAccountCommandHandler;
import com.banksolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.banksolution.account.cmd.commands.transaction.ITransactionCommandHandler;
import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.cqrs.core.infrastructure.CommandDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.banksolution.account.cmd.client")
@RequiredArgsConstructor
public class CommandApplication {

    private final CommandDispatcher commandDispatcher;
    private final IAccountCommandHandler accountCommandHandler;
    private final ITransactionCommandHandler transactionCommandHandler;

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(OpenAccountCommand.class, accountCommandHandler::handle);
        commandDispatcher.registerHandler(TransactionCommand.class, transactionCommandHandler::handle);
    }

    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }
}
