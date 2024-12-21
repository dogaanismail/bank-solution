package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.domain.AccountBalance;
import com.banksolution.account.cmd.fixtures.account.AccountBalanceEventFixtures;
import com.banksolution.account.cmd.fixtures.account.AccountBalanceFixtures;
import com.banksolution.common.events.AccountBalanceEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountBalanceEventFactoryTest {


    @Test
    void shouldReturnAccountBalanceEvent() {

        AccountBalance accountBalance = AccountBalanceFixtures.getAccountBalance();

        AccountBalanceEvent accountBalanceEvent = AccountBalanceEventFactory
                .getAccountBalanceEvent(accountBalance);

        Assertions.assertNotNull(accountBalanceEvent);
        Assertions.assertEquals(5, accountBalanceEvent.getCustomerId());
        Assertions.assertEquals("a66f26b5-f661-4372-b441-bd1c8ef35657", accountBalanceEvent.getAccountBalanceId());
        Assertions.assertNotNull(accountBalanceEvent.getAccountId());
        Assertions.assertEquals("EUR", accountBalanceEvent.getCurrencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), accountBalanceEvent.getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(25), accountBalanceEvent.getAvailableBalance());
    }

    @Test
    void shouldReturnAccountBalanceEventFixture() {

        AccountBalanceEvent accountBalanceEvent = AccountBalanceEventFixtures
                .getAccountBalanceEvent();

        Assertions.assertNotNull(accountBalanceEvent);
        Assertions.assertEquals(50, accountBalanceEvent.getCustomerId());
        Assertions.assertEquals("a66f26b5-f661-4372-b441-bd1c8ef35657", accountBalanceEvent.getAccountBalanceId());
        Assertions.assertEquals("b97d5284-8234-4071-9495-a12ff8bf1ea5", accountBalanceEvent.getAccountId());
        Assertions.assertEquals("EUR", accountBalanceEvent.getCurrencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), accountBalanceEvent.getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(25), accountBalanceEvent.getAvailableBalance());
    }
}