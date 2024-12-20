package com.banksolution.account.cmd.fixtures.transaction;

import com.banksolution.common.enums.TransactionDirection;
import com.banksolution.common.enums.TransactionStatus;
import com.banksolution.common.events.TransactionCreatedEvent;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class TransactionCreatedEventFixtures {

    public static TransactionCreatedEvent getTransactionCreatedEvent() {

        return TransactionCreatedEvent.builder()
                .accountId(UUID.fromString("103e114b-3c2f-4a27-a933-5f2836fc3fdf").toString())
                .direction(TransactionDirection.IN)
                .amount(BigDecimal.valueOf(50))
                .status(TransactionStatus.SUCCESS.toString())
                .currencyCode("EUR")
                .description("Sample transaction")
                .createdAt(Instant.now().toString())
                .balanceAfterTxn(BigDecimal.valueOf(100))
                .version(1)
                .build();
    }

    public static TransactionCreatedEvent getFailedTransactionCreatedEvent() {

        return TransactionCreatedEvent.builder()
                .accountId(UUID.fromString("103e114b-3c2f-4a27-a933-5f2836fc3fdf").toString())
                .direction(TransactionDirection.IN)
                .amount(BigDecimal.valueOf(50))
                .status(TransactionStatus.ERROR.toString())
                .currencyCode("EUR")
                .description("Sample transaction")
                .createdAt(Instant.now().toString())
                .balanceAfterTxn(BigDecimal.valueOf(100))
                .version(1)
                .build();
    }
}
