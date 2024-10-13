package com.banksolution.account.query.infrastructure.handlers.transaction;

import com.banksolution.account.query.domain.AccountBalance;
import com.banksolution.account.query.domain.AccountTransaction;
import com.banksolution.account.query.factory.transaction.AccountTransactionFactory;
import com.banksolution.account.query.mappers.AccountBalanceMapper;
import com.banksolution.account.query.mappers.TransactionMapper;
import com.banksolution.common.events.FundsDepositedEvent;
import com.banksolution.common.events.FundsWithDrawnEvent;
import com.banksolution.common.events.TransactionCreatedEvent;
import com.banksolution.common.events.TransactionFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionEventHandler implements ITransactionEventHandler {

    private final AccountBalanceMapper accountBalanceMapper;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public void on(FundsDepositedEvent fundsDepositedEvent) {

        try {
            AccountBalance accountBalance = accountBalanceMapper.getBalance(
                    fundsDepositedEvent.getAccountId(),
                    fundsDepositedEvent.getCurrencyCode()
            );

            if (accountBalance == null) {
                return;
            }

            BigDecimal currentBalance = accountBalance.getBalance();
            BigDecimal latestBalance = currentBalance.add(fundsDepositedEvent.getAmount());
            accountBalance.setBalance(latestBalance);

            accountBalanceMapper.updateAccountBalance(accountBalance);
        } catch (Exception exception) {
            log.error("Error while updating account balance!", exception);
            throw exception;
        }
    }

    @Override
    @Transactional
    public void on(FundsWithDrawnEvent fundsWithDrawnEvent) {

        try {
            AccountBalance accountBalance = accountBalanceMapper.getBalance(
                    fundsWithDrawnEvent.getAccountId(),
                    fundsWithDrawnEvent.getCurrencyCode()
            );

            if (accountBalance == null)
                return;

            BigDecimal currentBalance = accountBalance.getBalance();
            BigDecimal latestBalance = currentBalance.subtract(fundsWithDrawnEvent.getAmount());
            accountBalance.setBalance(latestBalance);

            accountBalanceMapper.updateAccountBalance(accountBalance);
        } catch (Exception exception) {
            log.error("Error while updating account balance!", exception);
            throw exception;
        }
    }

    @Override
    @Transactional
    public void on(TransactionCreatedEvent transactionCreatedEvent) {

        try {
            AccountTransaction accountTransaction = AccountTransactionFactory
                    .getAccountTransaction(transactionCreatedEvent);
            transactionMapper.insertTransaction(accountTransaction);
        } catch (Exception exception) {
            log.error("Error while creating a transaction!", exception);
            throw exception;
        }
    }

    @Override
    @Transactional
    public void on(TransactionFailedEvent transactionFailedEvent) {

        try {
            AccountTransaction accountTransaction = AccountTransactionFactory
                    .getFailedAccountTransaction(transactionFailedEvent);
            transactionMapper.insertTransaction(accountTransaction);
        } catch (Exception exception) {
            log.error("Error while creating a failed transaction!", exception);
            throw exception;
        }
    }
}
