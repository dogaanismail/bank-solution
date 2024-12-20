package com.banksolution.account.cmd.factory.transaction;

import com.banksolution.account.cmd.dto.TransactionCreateResponse;
import com.banksolution.account.cmd.fixtures.transaction.TransactionCreateResponseFixtures;
import com.banksolution.common.enums.TransactionDirection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TransactionCreateResponseFactoryTest {

    @Test
    void shouldReturnTransactionCreateResponse() {

        TransactionCreateResponse transactionCreateResponse = TransactionCreateResponseFixtures
                .getTransactionCreateResponse();

        Assertions.assertNotNull(transactionCreateResponse);
        Assertions.assertEquals("9c198744-6d4c-4d27-897b-1d0731105cad", transactionCreateResponse.accountId());
        Assertions.assertEquals("81fd10a9-d561-47c0-a6bb-44abb0fb522e", transactionCreateResponse.transactionId());
        Assertions.assertEquals(TransactionDirection.IN, transactionCreateResponse.direction());
        Assertions.assertEquals(BigDecimal.valueOf(50), transactionCreateResponse.amount());
        Assertions.assertEquals("EUR", transactionCreateResponse.currencyCode());
        Assertions.assertEquals("sample transaction", transactionCreateResponse.description());
        Assertions.assertEquals(BigDecimal.valueOf(75), transactionCreateResponse.balanceAfterTxn());
    }
}