package com.bankingsolution.account.cmd.commands.transaction;


import com.bankingsolution.account.cmd.domain.AccountTransaction;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionCommandHandler implements ITransactionCommandHandler{

    @Autowired
    private EventSourcingHandler<AccountTransaction> eventSourcingHandlers;

    @Override
    public void handle(TransactionCommand command) {
        AccountTransaction transaction = new AccountTransaction();

        eventSourcingHandlers.save(transaction);
    }
}
