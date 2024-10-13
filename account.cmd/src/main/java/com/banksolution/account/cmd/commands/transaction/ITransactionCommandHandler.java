package com.banksolution.account.cmd.commands.transaction;

import com.banksolution.cqrs.core.generics.ResponseModel;

public interface ITransactionCommandHandler {

    ResponseModel handle(TransactionCommand command);
}
