package com.bankingsolution.account.cmd.commands.transaction;

public interface ITransactionCommandHandler {
    void handle(TransactionCommand command);
}
