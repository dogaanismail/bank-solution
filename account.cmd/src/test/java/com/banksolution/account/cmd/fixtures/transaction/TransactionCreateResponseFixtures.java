package com.banksolution.account.cmd.fixtures.transaction;

import com.banksolution.account.cmd.dto.TransactionCreateResponse;
import com.banksolution.common.enums.TransactionDirection;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class TransactionCreateResponseFixtures {

    public static TransactionCreateResponse getTransactionCreateResponse() {

        return new TransactionCreateResponse(
                UUID.fromString("9c198744-6d4c-4d27-897b-1d0731105cad").toString(),
                UUID.fromString("81fd10a9-d561-47c0-a6bb-44abb0fb522e").toString(),
                TransactionDirection.IN,
                BigDecimal.valueOf(50),
                "EUR",
                "sample transaction",
                BigDecimal.valueOf(75)
        );
    }
}
