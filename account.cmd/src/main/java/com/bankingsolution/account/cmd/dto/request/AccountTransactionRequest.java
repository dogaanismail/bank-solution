package com.bankingsolution.account.cmd.dto.request;

import com.bankingsolution.common.enums.TransactionDirection;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AccountTransactionRequest(@NotEmpty String accountId,
                                        @NotEmpty BigDecimal amount,
                                        @NotEmpty String currency,
                                        @NotNull TransactionDirection direction,
                                        @NotEmpty String description) { }
