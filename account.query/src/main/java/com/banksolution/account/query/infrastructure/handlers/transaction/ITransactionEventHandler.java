package com.banksolution.account.query.infrastructure.handlers.transaction;

import com.banksolution.common.events.FundsDepositedEvent;
import com.banksolution.common.events.FundsWithDrawnEvent;
import com.banksolution.common.events.TransactionCreatedEvent;
import com.banksolution.common.events.TransactionFailedEvent;

public interface ITransactionEventHandler {
    void on(FundsDepositedEvent fundsDepositedEvent);

    void on(FundsWithDrawnEvent fundsWithDrawnEvent);

    void on(TransactionCreatedEvent transactionCreatedEvent);

    void on(TransactionFailedEvent transactionFailedEvent);
}
