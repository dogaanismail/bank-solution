package com.bankingsolution.account.query.infrastructure.handlers.accounting;

import com.bankingsolution.account.query.domain.Account;
import com.bankingsolution.account.query.domain.AccountBalance;
import com.bankingsolution.account.query.mappers.AccountBalanceMapper;
import com.bankingsolution.account.query.mappers.AccountMapper;
import com.bankingsolution.common.events.AccountOpenedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountEventHandler implements IAccountEventHandler {

    private final AccountMapper accountMapper;
    private final AccountBalanceMapper accountBalanceMapper;

    public AccountEventHandler(
            AccountMapper accountMapper,
            AccountBalanceMapper accountBalanceMapper) {
        this.accountMapper = accountMapper;
        this.accountBalanceMapper = accountBalanceMapper;
    }

    @Override
    @Transactional
    public void on(AccountOpenedEvent event) {

        try {
            final var bankAccount = Account.builder()
                    .accountId(event.getAccountId())
                    .customerId(event.getCustomerId())
                    .country(event.getCountry())
                    .build();

            accountMapper.insertAccount(bankAccount);

            for (final var balanceEvent : event.getAccountBalances()) {
                final var accountBalance = AccountBalance.builder()
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
            log.error("Error while creating an account and an account balance!", exception);
            throw exception;
        }
    }
}
