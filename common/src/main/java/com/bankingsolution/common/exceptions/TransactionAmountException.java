package com.bankingsolution.common.exceptions;

public class TransactionAmountException extends RuntimeException {
    public TransactionAmountException(String message) {
        super(message);
    }
}
