package com.bankingsolution.account.cmd.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCreateResponse {
    private String accountId;
    private String transactionId;
    private String direction;
    private BigDecimal amount;
    private String currencyCode;
    private String description;
    private BigDecimal balanceAfterTxn;
}
