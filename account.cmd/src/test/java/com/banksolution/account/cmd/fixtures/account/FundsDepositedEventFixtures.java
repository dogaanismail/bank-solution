package com.banksolution.account.cmd.fixtures.account;

import com.banksolution.common.events.FundsDepositedEvent;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class FundsDepositedEventFixtures {

    public static FundsDepositedEvent getFundsDepositedEvent() {

        return FundsDepositedEvent.builder()
                .customerId(5L)
                .accountBalanceId(UUID.fromString("ab671beb-fff6-4e5c-8a8e-10815ad994a5").toString())
                .accountId(UUID.fromString("97e0dcc4-f57d-4e2d-a90a-af8d6fe1c164").toString())
                .currencyCode("EUR")
                .amount(BigDecimal.valueOf(50))
                .version(1)
                .build();
    }
}
