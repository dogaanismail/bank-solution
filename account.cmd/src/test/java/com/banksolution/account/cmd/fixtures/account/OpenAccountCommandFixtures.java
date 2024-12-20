package com.banksolution.account.cmd.fixtures.account;

import com.banksolution.account.cmd.commands.accounting.OpenAccountCommand;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class OpenAccountCommandFixtures {

    public static OpenAccountCommand getOpenAccountCommand() {

        return OpenAccountCommand.builder()
                .customerId(5L)
                .country("EST")
                .currencies(List.of("EUR"))
                .build();
    }
}
