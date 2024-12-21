package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.banksolution.common.events.AccountOpenedEvent;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class AccountOpenedEventFactory {

    public static AccountOpenedEvent getAccountOpenedEvent(
            OpenAccountCommand openAccountCommand) {

        return AccountOpenedEvent.builder()
                .id(openAccountCommand.getId())
                .accountId(UUID.randomUUID().toString())
                .customerId(openAccountCommand.getCustomerId())
                .country(openAccountCommand.getCountry())
                .build();
    }
}
