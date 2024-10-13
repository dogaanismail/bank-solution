package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.dto.OpenAccountResponse;
import com.banksolution.account.cmd.fixtures.account.OpenAccountResponseFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class OpenAccountResponseFactoryTest {

    @Test
    void shouldReturnOpenAccountResponse() {

        OpenAccountResponse openAccountResponse = OpenAccountResponseFixtures
                .getOpenAccountResponse();

        Assertions.assertNotNull(openAccountResponse);
        Assertions.assertEquals("6741332f-ae16-4cf5-aff2-6893622aceea", openAccountResponse.accountId());
        Assertions.assertEquals(50L, openAccountResponse.customerId());
        Assertions.assertNotNull(openAccountResponse.accountBalances());
        Assertions.assertEquals("EUR", openAccountResponse.accountBalances().getFirst().currencyCode());
        Assertions.assertEquals(BigDecimal.valueOf(50), openAccountResponse.accountBalances().getFirst().balance());
    }
}