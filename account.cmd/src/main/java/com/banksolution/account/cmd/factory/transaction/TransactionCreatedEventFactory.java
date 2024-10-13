package com.banksolution.account.cmd.factory.transaction;

import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.common.enums.TransactionStatus;
import com.banksolution.common.events.TransactionCreatedEvent;
import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class TransactionCreatedEventFactory {

    public static TransactionCreatedEvent getTransactionCreatedEvent(
            TransactionCommand transactionCommand) {

        return TransactionCreatedEvent.builder()
                .id(transactionCommand.getId())
                .accountId(transactionCommand.getAccountId())
                .direction(transactionCommand.getDirection())
                .amount(transactionCommand.getAmount())
                .currencyCode(transactionCommand.getCurrency())
                .description(transactionCommand.getDescription())
                .createdAt(Instant.now().toString())
                .status(TransactionStatus.SUCCESS.toString())
                .balanceAfterTxn(transactionCommand.getBalanceAfterTxn())
                .build();
    }
}
