package com.bankingsolution.account.query.infrastructure.handlers;

import com.bankingsolution.account.query.domain.Account;
import com.bankingsolution.account.query.mappers.AccountMapper;
import com.bankingsolution.common.events.AccountOpenedEvent;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {

    private final AccountMapper accountMapper;

    public AccountEventHandler(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount =
                Account.builder()
                        .accountId(event.getId())
                        .customerId(event.getCustomerId())
                        .build();

        accountMapper.insertAccount(bankAccount);
    }
}
