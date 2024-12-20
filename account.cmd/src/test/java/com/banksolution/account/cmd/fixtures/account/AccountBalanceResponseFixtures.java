package com.banksolution.account.cmd.fixtures.account;

import com.banksolution.account.cmd.dto.AccountBalanceResponse;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class AccountBalanceResponseFixtures {

    public static AccountBalanceResponse getAccountBalanceResponse() {

        return new AccountBalanceResponse(
                "EUR",
                BigDecimal.valueOf(50)
        );
    }
}
