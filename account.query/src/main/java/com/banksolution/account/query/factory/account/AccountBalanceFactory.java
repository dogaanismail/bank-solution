package com.banksolution.account.query.factory.account;

import com.banksolution.account.query.domain.AccountBalance;
import com.banksolution.common.events.AccountBalanceEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountBalanceFactory {

    public static AccountBalance getAccountBalance(
            AccountBalanceEvent accountBalanceEvent) {

        return AccountBalance.builder()
                .accountBalanceId(accountBalanceEvent.getAccountBalanceId())
                .accountId(accountBalanceEvent.getAccountId())
                .customerId(accountBalanceEvent.getCustomerId())
                .currencyCode(accountBalanceEvent.getCurrencyCode())
                .balance(accountBalanceEvent.getBalance())
                .availableBalance(accountBalanceEvent.getAvailableBalance())
                .build();
    }
}
