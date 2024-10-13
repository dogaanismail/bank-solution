package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.domain.AccountBalance;
import com.banksolution.common.events.AccountBalanceEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountBalanceFactory {

    public static AccountBalance getAccountBalance(
            AccountBalanceEvent accountBalanceEvent) {

        return new AccountBalance(
                accountBalanceEvent.getCustomerId(),
                accountBalanceEvent.getAccountBalanceId(),
                accountBalanceEvent.getAccountId(),
                accountBalanceEvent.getCurrencyCode(),
                accountBalanceEvent.getBalance(),
                accountBalanceEvent.getAvailableBalance()
        );
    }
}
