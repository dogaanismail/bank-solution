package com.bankingsolution.account.query.dto;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;


public class BalanceResponse {
    private String currencyCode;
    private BigDecimal balance;

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
}
