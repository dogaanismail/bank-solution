package com.bankingsolution.account.cmd.domain;

import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
public class AccountTransaction extends AggregateRoot {

    private Long transactionId;
    private Long accountBalanceId;
    private int direction;
    private BigDecimal amount;
    private String description;
    private int status;
    private Timestamp transactionTime;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAccountBalanceId() {
        return accountBalanceId;
    }

    public void setAccountBalanceId(Long accountBalanceId) {
        this.accountBalanceId = accountBalanceId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }
}
