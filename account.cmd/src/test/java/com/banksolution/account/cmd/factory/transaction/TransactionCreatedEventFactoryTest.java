package com.banksolution.account.cmd.factory.transaction;

import com.banksolution.account.cmd.fixtures.transaction.TransactionCreatedEventFixtures;
import com.banksolution.common.enums.TransactionDirection;
import com.banksolution.common.enums.TransactionStatus;
import com.banksolution.common.events.TransactionCreatedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TransactionCreatedEventFactoryTest {

    @Test
    void shouldReturnTransactionCreatedEvent() {

        TransactionCreatedEvent transactionCreatedEvent = TransactionCreatedEventFixtures
                .getTransactionCreatedEvent();

        Assertions.assertNotNull(transactionCreatedEvent);
        Assertions.assertEquals("103e114b-3c2f-4a27-a933-5f2836fc3fdf", transactionCreatedEvent.getAccountId());
        Assertions.assertEquals(TransactionDirection.IN, transactionCreatedEvent.getDirection());
        Assertions.assertEquals(BigDecimal.valueOf(50), transactionCreatedEvent.getAmount());
        Assertions.assertEquals(TransactionStatus.SUCCESS.toString(), transactionCreatedEvent.getStatus());
        Assertions.assertEquals("EUR", transactionCreatedEvent.getCurrencyCode());
        Assertions.assertEquals("Sample transaction", transactionCreatedEvent.getDescription());
        Assertions.assertNotNull(transactionCreatedEvent.getCreatedAt());
        Assertions.assertEquals(BigDecimal.valueOf(100), transactionCreatedEvent.getBalanceAfterTxn());
        Assertions.assertEquals(1, transactionCreatedEvent.getVersion());
    }

    @Test
    void shouldReturnFailedTransactionCreatedEvent() {

        TransactionCreatedEvent transactionCreatedEvent = TransactionCreatedEventFixtures
                .getFailedTransactionCreatedEvent();

        Assertions.assertNotNull(transactionCreatedEvent);
        Assertions.assertEquals("103e114b-3c2f-4a27-a933-5f2836fc3fdf", transactionCreatedEvent.getAccountId());
        Assertions.assertEquals(TransactionDirection.IN, transactionCreatedEvent.getDirection());
        Assertions.assertEquals(BigDecimal.valueOf(50), transactionCreatedEvent.getAmount());
        Assertions.assertEquals(TransactionStatus.ERROR.toString(), transactionCreatedEvent.getStatus());
        Assertions.assertEquals("EUR", transactionCreatedEvent.getCurrencyCode());
        Assertions.assertEquals("Sample transaction", transactionCreatedEvent.getDescription());
        Assertions.assertNotNull(transactionCreatedEvent.getCreatedAt());
        Assertions.assertEquals(BigDecimal.valueOf(100), transactionCreatedEvent.getBalanceAfterTxn());
        Assertions.assertEquals(1, transactionCreatedEvent.getVersion());
    }
}