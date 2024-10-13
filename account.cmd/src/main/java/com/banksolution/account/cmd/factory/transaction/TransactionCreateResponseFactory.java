package com.banksolution.account.cmd.factory.transaction;

import com.banksolution.account.cmd.domain.AccountTransaction;
import com.banksolution.account.cmd.dto.TransactionCreateResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionCreateResponseFactory {

    public static TransactionCreateResponse getTransactionCreateResponse(
            AccountTransaction accountTransaction) {

        return new TransactionCreateResponse(
                accountTransaction.getAccountId(),
                accountTransaction.getId(),
                accountTransaction.getDirection(),
                accountTransaction.getAmount(),
                accountTransaction.getCurrency(),
                accountTransaction.getDescription(),
                accountTransaction.getBalanceAfterTxn()
        );
    }
}
