package com.banksolution.account.cmd.factory.account;

import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.common.events.FundsWithDrawnEvent;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FundsWithDrawnEventFactory {

    public static FundsWithDrawnEvent getFundsWithDrawnEvent(
            String id,
            TransactionCommand transactionCommand) {

        return FundsWithDrawnEvent.builder()
                .id(id)
                .accountId(transactionCommand.getAccountId())
                .currencyCode(transactionCommand.getCurrency())
                .amount(transactionCommand.getAmount())
                .build();
    }
}
