package com.banksolution.account.cmd.domain;

import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.account.cmd.factory.transaction.TransactionCreatedEventFactory;
import com.banksolution.account.cmd.factory.transaction.TransactionFailedEventFactory;
import com.banksolution.common.enums.TransactionDirection;
import com.banksolution.common.events.TransactionCreatedEvent;
import com.banksolution.common.events.TransactionFailedEvent;
import com.banksolution.cqrs.core.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AccountTransaction extends AggregateRoot {

    private String accountId;
    private TransactionDirection direction;
    private BigDecimal amount;
    private String status;
    private String createdAt;
    private String description;
    private String currency;
    private BigDecimal balanceAfterTxn;
    private Boolean active;

    public void apply(TransactionCreatedEvent transactionCreatedEvent) {

        this.id = transactionCreatedEvent.getId();
        this.active = true;
        this.accountId = transactionCreatedEvent.getAccountId();
        this.direction = transactionCreatedEvent.getDirection();
        this.amount = transactionCreatedEvent.getAmount();
        this.status = transactionCreatedEvent.getStatus();
        this.createdAt = transactionCreatedEvent.getCreatedAt();
        this.description = transactionCreatedEvent.getDescription();
        this.currency = transactionCreatedEvent.getCurrencyCode();
        this.balanceAfterTxn = transactionCreatedEvent.getBalanceAfterTxn();
    }

    public void apply(TransactionFailedEvent transactionFailedEvent) {

        this.id = transactionFailedEvent.getId();
        this.active = true;
        this.accountId = transactionFailedEvent.getAccountId();
        this.direction = transactionFailedEvent.getDirection();
        this.amount = transactionFailedEvent.getAmount();
        this.status = transactionFailedEvent.getStatus();
        this.createdAt = transactionFailedEvent.getCreatedAt();
        this.description = transactionFailedEvent.getDescription();
        this.currency = transactionFailedEvent.getCurrencyCode();
    }

    public void createTransaction(TransactionCommand transactionCommand) {

        TransactionCreatedEvent transactionCreatedEvent =
                TransactionCreatedEventFactory.getTransactionCreatedEvent(transactionCommand);

        raiseEvent(
                transactionCreatedEvent
        );
    }

    public void createFailedTransaction(TransactionCommand transactionCommand) {

        TransactionFailedEvent transactionFailedEvent =
                TransactionFailedEventFactory.getTransactionFailedEvent(transactionCommand);

        raiseEvent(
                transactionFailedEvent
        );
    }
}
