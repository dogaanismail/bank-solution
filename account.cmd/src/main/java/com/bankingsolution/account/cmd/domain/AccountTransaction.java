package com.bankingsolution.account.cmd.domain;

import com.bankingsolution.account.cmd.commands.transaction.TransactionCommand;
import com.bankingsolution.common.enums.TransactionStatus;
import com.bankingsolution.common.events.TransactionCreatedEvent;
import com.bankingsolution.common.events.TransactionFailedEvent;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

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

    public void createTransaction(TransactionCommand command) {
        TransactionCreatedEvent transactionCreatedEvent =
                TransactionCreatedEvent.builder()
                        .id(command.getId())
                        .accountId(command.getAccountId())
                        .direction(command.getDirection())
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

    public void crateTransactionFailedData(TransactionCommand command) {
        TransactionFailedEvent transactionFailedEvent =
                TransactionFailedEvent.builder()
                        .id(command.getId())
                        .accountId(command.getAccountId())
                        .direction(command.getDirection())
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private Boolean active;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalanceAfterTxn() {
        return balanceAfterTxn;
    }

    public void setBalanceAfterTxn(BigDecimal balanceAfterTxn) {
        this.balanceAfterTxn = balanceAfterTxn;
    }
}
