package com.bankingsolution.account.cmd.commands;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
}
