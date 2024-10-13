package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.domain.AccountAggregate;
import com.banksolution.account.cmd.dto.OpenAccountResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenAccountResponseFactory {

    public static OpenAccountResponse getOpenAccountResponse(
            AccountAggregate accountAggregate) {

        return new OpenAccountResponse(
                accountAggregate.getId(),
                accountAggregate.getCustomerId(),
                accountAggregate.getAccountBalances()
                        .stream()
                        .map(AccountBalanceResponseFactory::getAccountBalanceResponse)
                        .toList()
        );
    }
}
