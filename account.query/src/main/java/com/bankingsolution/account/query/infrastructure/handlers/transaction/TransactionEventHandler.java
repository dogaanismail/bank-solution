package com.bankingsolution.account.query.infrastructure.handlers.transaction;

import com.bankingsolution.account.query.domain.AccountTransaction;
import com.bankingsolution.account.query.infrastructure.handlers.accounting.AccountEventHandler;
import com.bankingsolution.account.query.mappers.AccountBalanceMapper;
import com.bankingsolution.account.query.mappers.TransactionMapper;
import com.bankingsolution.common.events.FundsDepositedEvent;
import com.bankingsolution.common.events.FundsWithDrawnEvent;
import com.bankingsolution.common.events.TransactionCreatedEvent;
import com.bankingsolution.common.events.TransactionFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionEventHandler implements ITransactionEventHandler {

    Logger logger = LoggerFactory.getLogger(TransactionEventHandler.class);

    private final AccountBalanceMapper accountBalanceMapper;
    private final TransactionMapper transactionMapper;

    public TransactionEventHandler(AccountBalanceMapper accountBalanceMapper,
                                   TransactionMapper transactionMapper) {
        this.accountBalanceMapper = accountBalanceMapper;
        this.transactionMapper = transactionMapper;
    }

    @Override
    @Transactional
    public void on(FundsDepositedEvent event) {

        try {
            var accountBalance = accountBalanceMapper.getBalance(event.getAccountId(), event.getCurrencyCode());

            if (accountBalance == null)
                return;

            var currentBalance = accountBalance.getBalance();
            var latestBalance = currentBalance.add(event.getAmount());
            accountBalance.setBalance(latestBalance);

            accountBalanceMapper.updateAccountBalance(accountBalance);
        } catch (Exception exception) {
            logger.error("Error while updating account balance!", exception);
            throw exception;
        }
    }

    @Override
    @Transactional
    public void on(FundsWithDrawnEvent event) {

        try {
            var accountBalance = accountBalanceMapper.getBalance(event.getAccountId(), event.getCurrencyCode());

            if (accountBalance == null)
                return;

            var currentBalance = accountBalance.getBalance();
            var latestBalance = currentBalance.subtract(event.getAmount());
            accountBalance.setBalance(latestBalance);

            accountBalanceMapper.updateAccountBalance(accountBalance);
        } catch (Exception exception) {
            logger.error("Error while updating account balance!", exception);
            throw exception;
        }
    }

    @Override
    @Transactional
    public void on(TransactionCreatedEvent event) {
        try {
            var transaction =
                    AccountTransaction.builder()
                            .transactionId(event.getId())
                            .accountId(event.getAccountId())
                            .direction(event.getDirection())
                            .amount(event.getAmount())
                            .status(event.getStatus())
                            .transactionTime(event.getTransactionTime())
                            .description(event.getDescription())
                            .balanceAfterTxn(event.getBalanceAfterTxn())
                            .currency(event.getCurrencyCode())
                            .build();

            transactionMapper.insertTransaction(transaction);

        } catch (Exception exception) {
            logger.error("Error while creating a transaction!", exception);
            throw exception;
        }
    }

    @Override
    @Transactional
    public void on(TransactionFailedEvent event) {
        try {
            var transaction =
                    AccountTransaction.builder()
                            .transactionId(event.getId())
                            .accountId(event.getAccountId())
                            .direction(event.getDirection())
                            .amount(event.getAmount())
                            .status(event.getStatus())
                            .transactionTime(event.getTransactionTime())
                            .description(event.getDescription())
                            .balanceAfterTxn(BigDecimal.ZERO)
                            .currency(event.getCurrencyCode())
                            .build();

            transactionMapper.insertTransaction(transaction);
        } catch (Exception exception) {
            logger.error("Error while creating a failed transaction!", exception);
            throw exception;
        }
    }
}
