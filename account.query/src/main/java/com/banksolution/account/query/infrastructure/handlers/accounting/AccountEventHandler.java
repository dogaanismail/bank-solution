package com.banksolution.account.query.infrastructure.handlers.accounting;

import com.banksolution.account.query.domain.Account;
import com.banksolution.account.query.domain.AccountBalance;
import com.banksolution.account.query.factory.account.AccountBalanceFactory;
import com.banksolution.account.query.factory.account.AccountFactory;
import com.banksolution.account.query.mappers.AccountBalanceMapper;
import com.banksolution.account.query.mappers.AccountMapper;
import com.banksolution.common.events.AccountBalanceEvent;
import com.banksolution.common.events.AccountOpenedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountEventHandler implements IAccountEventHandler {

    private final AccountMapper accountMapper;
    private final AccountBalanceMapper accountBalanceMapper;

    @Override
    @Transactional
    public void on(AccountOpenedEvent accountOpenedEvent) {

        try {

            Account account = AccountFactory.getAccount(accountOpenedEvent);
            accountMapper.insertAccount(account);

            for (AccountBalanceEvent accountBalanceEvent : accountOpenedEvent.getAccountBalances()) {

                AccountBalance accountBalance =
                        AccountBalanceFactory.getAccountBalance(accountBalanceEvent);
                accountBalanceMapper.insertAccountBalance(accountBalance);
            }
        } catch (Exception exception) {
            log.error("Error while creating an account and an account balance!", exception);
            throw exception;
        }
    }
}
