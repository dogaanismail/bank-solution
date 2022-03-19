package com.bankingsolution.account.query.infrastructure.handlers;

import com.bankingsolution.account.query.domain.Account;
import com.bankingsolution.account.query.mappers.AccountMapper;
import com.bankingsolution.common.events.AccountOpenedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {

    @Autowired
    private final AccountMapper accountMapper;

    public AccountEventHandler(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount =
                Account.builder()
                        .accountId(event.getAccountId())
                        .customerId(event.getCustomerId())
                        .country(event.getCountry())
                        .build();

        accountMapper.insertAccount(bankAccount);
    }
}
