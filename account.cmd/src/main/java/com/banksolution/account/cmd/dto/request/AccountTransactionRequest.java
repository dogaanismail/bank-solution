package com.banksolution.account.cmd.dto.request;

import com.banksolution.common.enums.TransactionDirection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountTransactionRequest(
        @NotEmpty String accountId,
        @NotEmpty BigDecimal amount,
        @NotEmpty String currency,
        @NotNull TransactionDirection direction,
        @NotEmpty String description) {
}
