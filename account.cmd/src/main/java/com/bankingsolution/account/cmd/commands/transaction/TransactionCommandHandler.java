package com.bankingsolution.account.cmd.commands.transaction;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.account.cmd.domain.AccountTransaction;
import com.bankingsolution.common.enums.TransactionDirection;
import com.bankingsolution.common.exceptions.AccountBalanceNotFoundException;
import com.bankingsolution.common.exceptions.AccountNotFoundException;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionCommandHandler implements ITransactionCommandHandler {

    @Autowired
    private EventSourcingHandler<AccountTransaction> transactionEventSourcingHandler;

    @Autowired
    private EventSourcingHandler<AccountAggregate> accountAggregateEventSourcingHandler;

    @Override
    public void handle(TransactionCommand command) throws AccountBalanceNotFoundException,
            AccountNotFoundException {

        var accountAggregate = accountAggregateEventSourcingHandler.getById(command.getAccountId());

        if (accountAggregate == null)
            throw new AccountNotFoundException("Account could not be found!");

        if (Objects.equals(command.getDirection(), TransactionDirection.IN.toString())) {
            accountAggregate.deposit(command);
        } else if (Objects.equals(command.getDirection(), TransactionDirection.OUT.toString())) {
            accountAggregate.withdraw(command);
        }
        else{
            throw new IllegalStateException("Invalid direction!");
        }

        accountAggregateEventSourcingHandler.save(accountAggregate);
    }
}
