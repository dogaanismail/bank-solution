package com.bankingsolution.account.cmd.domain;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class AccountBalance {
    private Long customerId;
    private String accountBalanceId;
    private String accountId;
    private String currencyCode;
    private BigDecimal balance;
    private BigDecimal availableBalance;

    public AccountBalance(
            String accountBalanceId,
            Long customerId,
            String currencyCode,
            BigDecimal balance,
            BigDecimal availableBalance,
            String accountId) {
        this.accountBalanceId = accountBalanceId;
        this.customerId = customerId;
        this.currencyCode = currencyCode;
        this.balance = balance;
        this.availableBalance = availableBalance;
        this.accountId = accountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountBalanceId() {
        return accountBalanceId;
    }

    public void setAccountBalanceId(String accountBalanceId) {
        this.accountBalanceId = accountBalanceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
}
