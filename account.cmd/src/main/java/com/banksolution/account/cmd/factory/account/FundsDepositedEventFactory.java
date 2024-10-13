package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.common.events.FundsDepositedEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FundsDepositedEventFactory {

    public static FundsDepositedEvent getFundsDepositedEvent(
            String id,
            TransactionCommand transactionCommand) {

        return FundsDepositedEvent.builder()
                .id(id)
                .accountId(transactionCommand.getAccountId())
                .currencyCode(transactionCommand.getCurrency())
                .amount(transactionCommand.getAmount())
                .build();
    }
}
