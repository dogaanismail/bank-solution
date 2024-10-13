package com.banksolution.account.query.factory.account;

import com.banksolution.account.query.domain.AccountBalance;
import com.banksolution.account.query.dto.BalanceResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BalanceResponseFactory {

    public static BalanceResponse getBalanceResponse(
            AccountBalance accountBalance) {

        return new BalanceResponse(
                accountBalance.getCurrencyCode(),
                accountBalance.getBalance()
        );
    }
}
