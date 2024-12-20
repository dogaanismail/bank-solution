package com.banksolution.account.cmd.fixtures.account;

import com.banksolution.account.cmd.domain.AccountBalance;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class AccountBalanceFixtures {

    public static AccountBalance getAccountBalance() {

        return new AccountBalance(
                5L,
                UUID.fromString("a66f26b5-f661-4372-b441-bd1c8ef35657").toString(),
                UUID.fromString("b97d5284-8234-4071-9495-a12ff8bf1ea5").toString(),
                "EUR",
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(25)
        );
    }
}
