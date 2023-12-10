package com.bankingsolution.account.cmd.domain;

import com.bankingsolution.account.cmd.commands.transaction.TransactionCommand;
import com.bankingsolution.common.enums.TransactionStatus;
import com.bankingsolution.common.events.TransactionCreatedEvent;
import com.bankingsolution.common.events.TransactionFailedEvent;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class AccountTransaction extends AggregateRoot {

    private String accountId;
    private String direction;
    private BigDecimal amount;
    private String status;
    private Timestamp transactionTime;
    private String description;
    private String currency;
    private BigDecimal balanceAfterTxn;
    private Boolean active;

    public void createTransaction(TransactionCommand command) {
        TransactionCreatedEvent transactionCreatedEvent =
                TransactionCreatedEvent.builder()
                        .id(command.getId())
                        .accountId(command.getAccountId())
                        .direction(command.getDirection().name())
                        .amount(command.getAmount())
                        .currencyCode(command.getCurrency())
                        .description(command.getDescription())
                        .transactionTime(new Timestamp(System.currentTimeMillis()))
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
                .direction(command.getDirection().name())
                .amount(command.getAmount())
                .currencyCode(command.getCurrency())
                .description(command.getDescription())
                .transactionTime(new Timestamp(System.currentTimeMillis()))
                .status(TransactionStatus.ERROR.toString())
                .build();

        raiseEvent(
                transactionFailedEvent
        );
    }
}
