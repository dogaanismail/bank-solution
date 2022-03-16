package com.bankingsolution.account.model;

import org.springframework.stereotype.Component;

@Component
public class Account {
    private Long accountId;
    private Long customerId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
