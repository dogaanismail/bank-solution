package com.banksolution.account.query.factory.account;

import com.banksolution.account.query.domain.Account;
import com.banksolution.common.events.AccountOpenedEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountFactory {

    public static Account getAccount(
            AccountOpenedEvent accountOpenedEvent) {

        return Account.builder()
                .accountId(accountOpenedEvent.getAccountId())
                .customerId(accountOpenedEvent.getCustomerId())
                .country(accountOpenedEvent.getCountry())
                .build();
    }
}
