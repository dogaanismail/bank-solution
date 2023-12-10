package com.bankingsolution.account.cmd.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AccountBalance {
    private Long customerId;
    private String accountBalanceId;
    private String accountId;
    private String currencyCode;
    private @NotEmpty BigDecimal balance;
    private BigDecimal availableBalance;

    public AccountBalance(
            String accountBalanceId,
            Long customerId,
            String currencyCode,
            BigDecimal balance,
            BigDecimal availableBalance,
            String accountId) {
        this.accountBalanceId = accountBalanceId;
        this.customerId = customerId;
        this.currencyCode = currencyCode;
        this.balance = balance;
        this.availableBalance = availableBalance;
        this.accountId = accountId;
    }

}
