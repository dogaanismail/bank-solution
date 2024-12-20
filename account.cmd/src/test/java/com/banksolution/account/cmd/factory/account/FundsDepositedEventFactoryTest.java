package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.fixtures.account.FundsDepositedEventFixtures;
import com.banksolution.common.events.FundsDepositedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class FundsDepositedEventFactoryTest {

    @Test
    void shouldReturnFundsDepositedEvent() {

        FundsDepositedEvent fundsDepositedEvent = FundsDepositedEventFixtures
                .getFundsDepositedEvent();

        Assertions.assertNotNull(fundsDepositedEvent);
        Assertions.assertEquals(5L, fundsDepositedEvent.getCustomerId());
        Assertions.assertEquals("ab671beb-fff6-4e5c-8a8e-10815ad994a5", fundsDepositedEvent.getAccountBalanceId());
        Assertions.assertEquals("97e0dcc4-f57d-4e2d-a90a-af8d6fe1c164", fundsDepositedEvent.getAccountId());
        Assertions.assertEquals("EUR", fundsDepositedEvent.getCurrencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), fundsDepositedEvent.getAmount());
        Assertions.assertEquals(1, fundsDepositedEvent.getVersion());
    }
}