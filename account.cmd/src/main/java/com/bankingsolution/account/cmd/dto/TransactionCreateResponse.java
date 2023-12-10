package com.bankingsolution.account.cmd.dto;

import com.bankingsolution.common.enums.TransactionDirection;

import java.math.BigDecimal;

public record TransactionCreateResponse(String accountId,
                                        String transactionId,
                                        TransactionDirection direction,
                                        BigDecimal amount,
                                        String currencyCode,
                                        String description,
                                        BigDecimal balanceAfterTxn) {
}
