package com.banksolution.account.cmd.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalance {
    private Long customerId;
    private String accountBalanceId;
    private String accountId;
    private String currencyCode;
    private @NotEmpty BigDecimal balance;
    private BigDecimal availableBalance;
}
