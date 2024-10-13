package com.banksolution.account.cmd.factory.transaction;

import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.common.enums.TransactionStatus;
import com.banksolution.common.events.TransactionFailedEvent;
import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class TransactionFailedEventFactory {

    public static TransactionFailedEvent getTransactionFailedEvent(
            TransactionCommand transactionCommand) {

        return TransactionFailedEvent.builder()
                .id(transactionCommand.getId())
                .accountId(transactionCommand.getAccountId())
                .direction(transactionCommand.getDirection())
                .amount(transactionCommand.getAmount())
                .currencyCode(transactionCommand.getCurrency())
                .description(transactionCommand.getDescription())
                .createdAt(Instant.now().toString())
                .status(TransactionStatus.ERROR.toString())
                .build();
    }
}
