package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.account.cmd.fixtures.account.FundsDepositedEventFixtures;
import com.banksolution.account.cmd.fixtures.transaction.TransactionCommandFixtures;
import com.banksolution.common.events.FundsDepositedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class FundsDepositedEventFactoryTest {

    @Test
    void shouldReturnFundsDepositedEvent() {

        String fundsEventId = UUID.randomUUID().toString();
        TransactionCommand transactionCommand = TransactionCommandFixtures
                .getTransactionCommand();

        FundsDepositedEvent fundsDepositedEvent = FundsDepositedEventFactory
                .getFundsDepositedEvent(fundsEventId, transactionCommand);

        Assertions.assertNotNull(fundsDepositedEvent);
        Assertions.assertEquals("599af76f-7526-4acc-ac5a-b5c2ae3c7d27", fundsDepositedEvent.getAccountId());
        Assertions.assertEquals("EUR", fundsDepositedEvent.getCurrencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), fundsDepositedEvent.getAmount());
        Assertions.assertEquals(0, fundsDepositedEvent.getVersion());
    }

    @Test
    void shouldReturnFundsDepositedEventFixture() {

        FundsDepositedEvent fundsDepositedEvent = FundsDepositedEventFixtures
                .getFundsDepositedEvent();

        Assertions.assertNotNull(fundsDepositedEvent);
        Assertions.assertEquals("97e0dcc4-f57d-4e2d-a90a-af8d6fe1c164", fundsDepositedEvent.getAccountId());
        Assertions.assertEquals("EUR", fundsDepositedEvent.getCurrencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), fundsDepositedEvent.getAmount());
        Assertions.assertEquals(1, fundsDepositedEvent.getVersion());
    }
}