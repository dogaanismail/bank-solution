package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.fixtures.account.FundsWithDrawnEventFixtures;
import com.banksolution.common.events.FundsWithDrawnEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class FundsWithDrawnEventFactoryTest {

    @Test
    void shouldReturnFundsWithDrawnEvent() {

        FundsWithDrawnEvent fundsWithDrawnEvent = FundsWithDrawnEventFixtures
                .getFundsWithDrawnEvent();

        Assertions.assertNotNull(fundsWithDrawnEvent);
        Assertions.assertEquals(5L, fundsWithDrawnEvent.getCustomerId());
        Assertions.assertEquals("c0ef4cef-5015-4bba-bbfc-1446e48c8837", fundsWithDrawnEvent.getAccountBalanceId());
        Assertions.assertEquals("8f485081-d48f-400a-80f6-941d6dc5e6bf", fundsWithDrawnEvent.getAccountId());
        Assertions.assertEquals("EUR", fundsWithDrawnEvent.getCurrencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), fundsWithDrawnEvent.getAmount());
        Assertions.assertEquals(1, fundsWithDrawnEvent.getVersion());
    }
}