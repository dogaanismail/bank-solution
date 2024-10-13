package com.banksolution.common.exceptions;

public class TransactionAmountException extends RuntimeException {
    public TransactionAmountException(String message) {
        super(message);
    }
}
