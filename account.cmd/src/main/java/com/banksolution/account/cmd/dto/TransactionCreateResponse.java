package com.banksolution.account.cmd.dto;

import com.banksolution.common.enums.TransactionDirection;

import java.math.BigDecimal;

public record TransactionCreateResponse(
        String accountId,
        String transactionId,
        TransactionDirection direction,
        BigDecimal amount,
        String currencyCode,
        String description,
        BigDecimal balanceAfterTxn) {
}
