package com.banksolution.account.cmd.fixtures.account;

import com.banksolution.common.events.AccountOpenedEvent;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class AccountOpenedEventFixtures {

    public static AccountOpenedEvent getAccountOpenedEvent() {

        return AccountOpenedEvent.builder()
                .customerId(5L)
                .country("EST")
                .accountId(UUID.fromString("1eba7bcb-3b99-4157-9586-76fe27c49829").toString())
                .accountBalances(List.of(AccountBalanceEventFixtures.getAccountBalanceEvent()))
                .version(1)
                .build();
    }
}
