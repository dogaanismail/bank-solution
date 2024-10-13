package com.banksolution.account.cmd.fixtures.account;

import com.banksolution.account.cmd.dto.OpenAccountResponse;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class OpenAccountResponseFixtures {

    public static OpenAccountResponse getOpenAccountResponse() {

        return new OpenAccountResponse(
                UUID.fromString("6741332f-ae16-4cf5-aff2-6893622aceea").toString(),
                50L,
                List.of(AccountBalanceResponseFixtures.getAccountBalanceResponse())
        );
    }
}
