package com.bankingsolution.account.query.infrastructure.handlers.transaction;

import com.bankingsolution.common.events.FundsDepositedEvent;
import com.bankingsolution.common.events.FundsWithDrawnEvent;
import com.bankingsolution.common.events.TransactionCreatedEvent;
import com.bankingsolution.common.events.TransactionFailedEvent;

public interface ITransactionEventHandler {
    void on(FundsDepositedEvent event);

    void on(FundsWithDrawnEvent event);

    void on(TransactionCreatedEvent event);

    void on(TransactionFailedEvent event);
}
