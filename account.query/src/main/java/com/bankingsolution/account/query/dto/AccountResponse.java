package com.bankingsolution.account.query.dto;

import java.util.List;


public class AccountResponse {
    private String accountId;
    private Long customerId;
    private List<BalanceResponse> accountBalances;

    public List<BalanceResponse> getAccountBalances() {
        return accountBalances;
    }

    public void setAccountBalances(List<BalanceResponse> accountBalances) {
        this.accountBalances = accountBalances;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
