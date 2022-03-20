package com.bankingsolution.common.exceptions;

public class AccountBalanceNotFoundException extends RuntimeException {
    public AccountBalanceNotFoundException(String message) {
        super(message);
    }
}
