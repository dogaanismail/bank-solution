package com.banksolution.account.query.infrastructure.handlers.transaction;

import com.banksolution.common.events.FundsDepositedEvent;
import com.banksolution.common.events.FundsWithDrawnEvent;
import com.banksolution.common.events.TransactionCreatedEvent;
import com.banksolution.common.events.TransactionFailedEvent;

public interface ITransactionEventHandler {
    void on(FundsDepositedEvent event);

    void on(FundsWithDrawnEvent event);

    void on(TransactionCreatedEvent event);

    void on(TransactionFailedEvent event);
}
