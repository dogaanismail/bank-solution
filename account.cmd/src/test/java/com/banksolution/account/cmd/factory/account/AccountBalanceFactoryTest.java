package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.domain.AccountBalance;
import com.banksolution.account.cmd.fixtures.account.AccountBalanceEventFixtures;
import com.banksolution.account.cmd.fixtures.account.AccountBalanceFixtures;
import com.banksolution.common.events.AccountBalanceEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountBalanceFactoryTest {

    @Test
    void shouldReturnAccountBalance() {

        AccountBalanceEvent accountBalanceEvent = AccountBalanceEventFixtures
                .getAccountBalanceEvent();

        AccountBalance accountBalance = AccountBalanceFactory
                .getAccountBalance(accountBalanceEvent);

        Assertions.assertNotNull(accountBalance);
        Assertions.assertEquals(50L, accountBalance.getCustomerId());
        Assertions.assertEquals("a66f26b5-f661-4372-b441-bd1c8ef35657", accountBalance.getAccountBalanceId());
        Assertions.assertEquals("b97d5284-8234-4071-9495-a12ff8bf1ea5", accountBalance.getAccountId());
        Assertions.assertEquals("EUR", accountBalance.getCurrencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), accountBalance.getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(25), accountBalance.getAvailableBalance());
    }

    @Test
    void shouldReturnAccountBalanceFixture() {

        AccountBalance accountBalance = AccountBalanceFixtures
                .getAccountBalance();

        Assertions.assertNotNull(accountBalance);
        Assertions.assertEquals(5L, accountBalance.getCustomerId());
        Assertions.assertEquals("a66f26b5-f661-4372-b441-bd1c8ef35657", accountBalance.getAccountBalanceId());
        Assertions.assertEquals("b97d5284-8234-4071-9495-a12ff8bf1ea5", accountBalance.getAccountId());
        Assertions.assertEquals("EUR", accountBalance.getCurrencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), accountBalance.getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(25), accountBalance.getAvailableBalance());
    }
}
