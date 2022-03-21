package com.bankingsolution.account.cmd.commands.transaction;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.account.cmd.domain.AccountTransaction;
import com.bankingsolution.common.enums.TransactionDirection;
import com.bankingsolution.common.exceptions.*;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class TransactionCommandHandler implements ITransactionCommandHandler {

    @Autowired
    private EventSourcingHandler<AccountTransaction> transactionEventSourcingHandler;

    @Autowired
    private EventSourcingHandler<AccountAggregate> accountAggregateEventSourcingHandler;

    @Override
    public void handle(TransactionCommand command) throws AccountBalanceNotFoundException,
            AccountNotFoundException,
            TransactionAmountException,
            TransactionDescriptonException, InvaildDirectionException, InsufficientFundsException {

        try {
            BigDecimal balanceAfterTxn = doTransaction(command);
            command.setBalanceAfterTxn(balanceAfterTxn);
            var transactionAggregate = new AccountTransaction();

            transactionAggregate.createTransaction(command);
            transactionEventSourcingHandler.save(transactionAggregate);

        } catch (Exception exception) {
            var failedTransactionAggregate = new AccountTransaction();
            failedTransactionAggregate.crateTransactionFailedData(command);
            transactionEventSourcingHandler.save(failedTransactionAggregate);
            throw exception;
        }
    }

    private BigDecimal doTransaction(TransactionCommand command) {
        var accountAggregate = accountAggregateEventSourcingHandler.getById(command.getAccountId());

        BigDecimal balanceAfterTxn = BigDecimal.ZERO;

        if (accountAggregate == null)
            throw new AccountNotFoundException("Account could not be found!");

        if (command.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new TransactionAmountException("Transaction amount can not be less than zero!");

        if (command.getDescription().isEmpty())
            throw new TransactionDescriptonException("Description can not be missing!");

        if (Objects.equals(command.getDirection(), TransactionDirection.IN.toString())) {
            balanceAfterTxn = accountAggregate.deposit(command);
        } else if (Objects.equals(command.getDirection(), TransactionDirection.OUT.toString())) {
            balanceAfterTxn = accountAggregate.withdraw(command);
        } else {
            throw new InvaildDirectionException("Invalid direction!");
        }

        accountAggregateEventSourcingHandler.save(accountAggregate);
        
        return balanceAfterTxn;
    }
}
