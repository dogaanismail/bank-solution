package com.bankingsolution.account.cmd.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class AccountTransactionRequest {

    @NotEmpty
    private String accountId;

    @NotEmpty
    private BigDecimal amount;

    @NotEmpty
    private String currency;

    @NotEmpty
    private String direction;

    @NotEmpty
    private String description;
}
