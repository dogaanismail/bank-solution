package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.banksolution.account.cmd.fixtures.account.AccountOpenedEventFixtures;
import com.banksolution.account.cmd.fixtures.account.OpenAccountCommandFixtures;
import com.banksolution.common.events.AccountOpenedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountOpenedEventFactoryTest {

    @Test
    void shouldReturnAccountOpenedEvent() {

        OpenAccountCommand openAccountCommand = OpenAccountCommandFixtures
                .getOpenAccountCommand();

        AccountOpenedEvent accountOpenedEvent = AccountOpenedEventFactory
                .getAccountOpenedEvent(openAccountCommand);

        Assertions.assertNotNull(accountOpenedEvent);
        Assertions.assertEquals(5L, accountOpenedEvent.getCustomerId());
        Assertions.assertEquals("EST", accountOpenedEvent.getCountry());
        Assertions.assertNotNull(accountOpenedEvent.getAccountId());
        Assertions.assertNull(accountOpenedEvent.getAccountBalances());
        Assertions.assertEquals(0, accountOpenedEvent.getVersion());
    }

    @Test
    void shouldReturnAccountOpenedEventFixture() {

        AccountOpenedEvent accountOpenedEvent = AccountOpenedEventFixtures
                .getAccountOpenedEvent();

        Assertions.assertNotNull(accountOpenedEvent);
        Assertions.assertEquals(5L, accountOpenedEvent.getCustomerId());
        Assertions.assertEquals("EST", accountOpenedEvent.getCountry());
        Assertions.assertEquals("1eba7bcb-3b99-4157-9586-76fe27c49829", accountOpenedEvent.getAccountId());
        Assertions.assertNotNull(accountOpenedEvent.getAccountBalances());
        Assertions.assertEquals(1, accountOpenedEvent.getVersion());
    }
}