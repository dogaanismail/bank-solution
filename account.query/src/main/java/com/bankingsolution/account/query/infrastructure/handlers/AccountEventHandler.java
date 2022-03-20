package com.bankingsolution.account.query.infrastructure.handlers;

import com.bankingsolution.account.query.domain.Account;
import com.bankingsolution.account.query.domain.AccountBalance;
import com.bankingsolution.account.query.mappers.AccountBalanceMapper;
import com.bankingsolution.account.query.mappers.AccountMapper;
import com.bankingsolution.common.events.AccountBalanceEvent;
import com.bankingsolution.common.events.AccountOpenedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountEventHandler implements EventHandler {

    Logger logger = LoggerFactory.getLogger(AccountEventHandler.class);

    @Autowired
    private final AccountMapper accountMapper;

    @Autowired
    private final AccountBalanceMapper accountBalanceMapper;

    public AccountEventHandler(AccountMapper accountMapper,
                               AccountBalanceMapper accountBalanceMapper) {
        this.accountMapper = accountMapper;
        this.accountBalanceMapper = accountBalanceMapper;
    }

    @Override
    @Transactional
    public void on(AccountOpenedEvent event) {
        try {
            var bankAccount =
                    Account.builder()
                            .accountId(event.getAccountId())
                            .customerId(event.getCustomerId())
                            .country(event.getCountry())
                            .build();

            accountMapper.insertAccount(bankAccount);

            for (AccountBalanceEvent balanceEvent : event.getAccountBalances()) {
                AccountBalance accountBalance =
                        AccountBalance.builder()
                                .accountBalanceId(balanceEvent.getAccountBalanceId())
                                .accountId(balanceEvent.getAccountId())
                                .customerId(balanceEvent.getCustomerId())
                                .currencyCode(balanceEvent.getCurrencyCode())
                                .balance(balanceEvent.getBalance())
                                .availableBalance(balanceEvent.getAvailableBalance())
                                .build();

                accountBalanceMapper.insertAccountBalance(accountBalance);

            }
        } catch (Exception exception) {
            logger.error("Error while creating account and account balance", exception);
            throw exception;
        }
    }
}
