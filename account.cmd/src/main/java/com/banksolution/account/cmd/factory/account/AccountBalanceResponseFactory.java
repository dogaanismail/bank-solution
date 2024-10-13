package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.domain.AccountBalance;
import com.banksolution.account.cmd.dto.AccountBalanceResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountBalanceResponseFactory {

    public static AccountBalanceResponse getAccountBalanceResponse(
            AccountBalance balance) {

        return new AccountBalanceResponse(
                balance.getCurrencyCode(),
                balance.getBalance()
        );
    }

}
