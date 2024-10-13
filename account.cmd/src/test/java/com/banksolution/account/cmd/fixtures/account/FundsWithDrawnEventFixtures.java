package com.banksolution.account.cmd.fixtures.account;

import com.banksolution.common.events.FundsWithDrawnEvent;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class FundsWithDrawnEventFixtures {

    public static FundsWithDrawnEvent getFundsWithDrawnEvent() {

        return FundsWithDrawnEvent.builder()
                .customerId(5L)
                .accountBalanceId(UUID.fromString("c0ef4cef-5015-4bba-bbfc-1446e48c8837").toString())
                .accountId(UUID.fromString("8f485081-d48f-400a-80f6-941d6dc5e6bf").toString())
                .currencyCode("EUR")
                .amount(BigDecimal.valueOf(50))
                .version(1)
                .build();
    }
}
