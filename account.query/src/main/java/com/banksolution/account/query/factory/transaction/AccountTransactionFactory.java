package com.banksolution.account.query.factory.transaction;

import com.banksolution.account.query.domain.AccountTransaction;
import com.banksolution.common.events.TransactionCreatedEvent;
import com.banksolution.common.events.TransactionFailedEvent;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class AccountTransactionFactory {

    public static AccountTransaction getAccountTransaction(
            TransactionCreatedEvent transactionCreatedEvent) {

        return AccountTransaction.builder()
                .transactionId(transactionCreatedEvent.getId())
                .accountId(transactionCreatedEvent.getAccountId())
                .direction(transactionCreatedEvent.getDirection().name())
                .amount(transactionCreatedEvent.getAmount())
                .status(transactionCreatedEvent.getStatus())
                .createdAt(transactionCreatedEvent.getCreatedAt())
                .description(transactionCreatedEvent.getDescription())
                .balanceAfterTxn(transactionCreatedEvent.getBalanceAfterTxn())
                .currency(transactionCreatedEvent.getCurrencyCode())
                .build();
    }

    public static AccountTransaction getFailedAccountTransaction(
            TransactionFailedEvent transactionFailedEvent) {

        return AccountTransaction.builder()
                .transactionId(transactionFailedEvent.getId())
                .accountId(transactionFailedEvent.getAccountId())
                .direction(transactionFailedEvent.getDirection().name())
                .amount(transactionFailedEvent.getAmount())
                .status(transactionFailedEvent.getStatus())
                .createdAt(transactionFailedEvent.getCreatedAt())
                .description(transactionFailedEvent.getDescription())
                .balanceAfterTxn(BigDecimal.ZERO)
                .currency(transactionFailedEvent.getCurrencyCode())
                .build();
    }
}
