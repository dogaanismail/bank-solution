package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.dto.AccountBalanceResponse;
import com.banksolution.account.cmd.fixtures.account.AccountBalanceResponseFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountBalanceResponseFactoryTest {

    @Test
    void shouldReturnAccountBalanceResponse() {

        AccountBalanceResponse accountBalanceResponse = AccountBalanceResponseFixtures
                .getAccountBalanceResponse();

        Assertions.assertNotNull(accountBalanceResponse);
        Assertions.assertEquals("EUR", accountBalanceResponse.currencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), accountBalanceResponse.balance());
    }
}