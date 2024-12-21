package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.domain.AccountBalance;
import com.banksolution.account.cmd.dto.AccountBalanceResponse;
import com.banksolution.account.cmd.fixtures.account.AccountBalanceFixtures;
import com.banksolution.account.cmd.fixtures.account.AccountBalanceResponseFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountBalanceResponseFactoryTest {

    @Test
    void shouldReturnAccountBalanceResponse() {

        AccountBalance accountBalance = AccountBalanceFixtures.getAccountBalance();

        AccountBalanceResponse accountBalanceResponse = AccountBalanceResponseFactory
                .getAccountBalanceResponse(accountBalance);

        Assertions.assertNotNull(accountBalanceResponse);
        Assertions.assertEquals("EUR", accountBalanceResponse.currencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), accountBalanceResponse.balance());
    }

    @Test
    void shouldReturnAccountBalanceResponseFixture() {

        AccountBalanceResponse accountBalanceResponse = AccountBalanceResponseFixtures
                .getAccountBalanceResponse();

        Assertions.assertNotNull(accountBalanceResponse);
        Assertions.assertEquals("EUR", accountBalanceResponse.currencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), accountBalanceResponse.balance());
    }
}