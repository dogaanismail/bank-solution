package com.banksolution.account.cmd.fixtures.transaction;

import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.common.enums.TransactionDirection;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class TransactionCommandFixtures {

    public static TransactionCommand getTransactionCommand() {

        return TransactionCommand.builder()
                .accountId(UUID.fromString("599af76f-7526-4acc-ac5a-b5c2ae3c7d27").toString())
                .amount(BigDecimal.valueOf(50))
                .currency("EUR")
                .direction(TransactionDirection.IN)
                .description("Sample description")
                .balanceAfterTxn(BigDecimal.valueOf(75))
                .build();
    }
}
