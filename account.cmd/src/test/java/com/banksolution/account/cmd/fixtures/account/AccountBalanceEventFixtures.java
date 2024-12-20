package com.banksolution.account.cmd.fixtures.account;

import com.banksolution.common.events.AccountBalanceEvent;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class AccountBalanceEventFixtures {

    public static AccountBalanceEvent getAccountBalanceEvent() {

        return AccountBalanceEvent.builder()
                .customerId(Long.valueOf("50"))
                .accountBalanceId(UUID.fromString("a66f26b5-f661-4372-b441-bd1c8ef35657").toString())
                .accountId(UUID.fromString("b97d5284-8234-4071-9495-a12ff8bf1ea5").toString())
                .currencyCode("EUR")
                .balance(BigDecimal.valueOf(50))
                .availableBalance(BigDecimal.valueOf(25))
                .build();
    }
}
