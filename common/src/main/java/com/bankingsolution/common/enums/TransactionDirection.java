package com.bankingsolution.common.enums;

import lombok.Getter;

@Getter
public enum TransactionDirection {
    IN(1),
    OUT(2);

    private final int value;

    TransactionDirection(int value) {
        this.value = value;
    }
}
