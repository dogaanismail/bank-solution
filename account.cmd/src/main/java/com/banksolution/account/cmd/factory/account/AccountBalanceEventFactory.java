package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.domain.AccountBalance;
import com.banksolution.common.events.AccountBalanceEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountBalanceEventFactory {

    public static AccountBalanceEvent getAccountBalanceEvent(
            AccountBalance accountBalance) {

        return AccountBalanceEvent.builder()
                .customerId(accountBalance.getCustomerId())
                .accountBalanceId(accountBalance.getAccountBalanceId())
                .accountId(accountBalance.getAccountId())
                .currencyCode(accountBalance.getCurrencyCode())
                .balance(accountBalance.getBalance())
                .availableBalance(accountBalance.getAvailableBalance())
                .build();
    }
}
