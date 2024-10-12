package com.bankingsolution.account.cmd.commands.transaction;

import com.bankingsolution.cqrs.core.generics.ResponseModel;

public interface ITransactionCommandHandler {

    ResponseModel handle(TransactionCommand command);
}
