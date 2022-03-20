package com.bankingsolution.account.query.infrastructure.handlers.transaction;

import com.bankingsolution.account.query.infrastructure.handlers.accounting.AccountEventHandler;
import com.bankingsolution.account.query.mappers.AccountBalanceMapper;
import com.bankingsolution.account.query.mappers.TransactionMapper;
import com.bankingsolution.common.events.FundsDepositedEvent;
import com.bankingsolution.common.events.FundsWithDrawnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionEventHandler implements ITransactionEventHandler {

    Logger logger = LoggerFactory.getLogger(AccountEventHandler.class);

    @Autowired
    private final AccountBalanceMapper accountBalanceMapper;

    @Autowired
    private final TransactionMapper transactionMapper;

    public TransactionEventHandler(AccountBalanceMapper accountBalanceMapper,
                                   TransactionMapper transactionMapper) {
        this.accountBalanceMapper = accountBalanceMapper;
        this.transactionMapper = transactionMapper;
    }

    @Override
    @Transactional
    public void on(FundsDepositedEvent event) {

    }

    @Override
    @Transactional
    public void on(FundsWithDrawnEvent event) {

    }
}
