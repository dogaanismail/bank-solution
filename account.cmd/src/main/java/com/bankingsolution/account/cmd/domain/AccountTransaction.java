package com.bankingsolution.account.cmd.domain;

import com.bankingsolution.account.cmd.commands.transaction.TransactionCommand;
import com.bankingsolution.common.enums.TransactionDirection;
import com.bankingsolution.common.enums.TransactionStatus;
import com.bankingsolution.common.events.TransactionCreatedEvent;
import com.bankingsolution.common.events.TransactionFailedEvent;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

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

    public void apply(TransactionCreatedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.accountId = event.getAccountId();
        this.direction = event.getDirection();
        this.amount = event.getAmount();
        this.status = event.getStatus();
        this.createdAt = event.getCreatedAt();
        this.description = event.getDescription();
        this.currency = event.getCurrencyCode();
        this.balanceAfterTxn = event.getBalanceAfterTxn();
    }

    public void apply(TransactionFailedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.accountId = event.getAccountId();
        this.direction = event.getDirection();
        this.amount = event.getAmount();
        this.status = event.getStatus();
        this.createdAt = event.getCreatedAt();
        this.description = event.getDescription();
        this.currency = event.getCurrencyCode();
    }

    public void createTransaction(TransactionCommand command) {
        final var transactionCreatedEvent = TransactionCreatedEvent.builder()
                .id(command.getId())
                .accountId(command.getAccountId())
                .direction(command.getDirection())
                .amount(command.getAmount())
                .currencyCode(command.getCurrency())
                .description(command.getDescription())
                .createdAt(Instant.now().toString())
                .status(TransactionStatus.SUCCESS.toString())
                .balanceAfterTxn(command.getBalanceAfterTxn())
                .build();

        raiseEvent(
                transactionCreatedEvent
        );
    }

    public void createTransactionFailedData(TransactionCommand command) {
        final var transactionFailedEvent = TransactionFailedEvent.builder()
                .id(command.getId())
                .accountId(command.getAccountId())
                .direction(command.getDirection())
                .amount(command.getAmount())
                .currencyCode(command.getCurrency())
                .description(command.getDescription())
                .createdAt(Instant.now().toString())
                .status(TransactionStatus.ERROR.toString())
                .build();

        raiseEvent(
                transactionFailedEvent
        );
    }
}
