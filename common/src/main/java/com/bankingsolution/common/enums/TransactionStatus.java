package com.bankingsolution.common.enums;

import lombok.Getter;

@Getter
public enum TransactionStatus {

    SUCCESS(1),
    ERROR(2);

    private final int value;

    TransactionStatus(int value) {
        this.value = value;
    }

}