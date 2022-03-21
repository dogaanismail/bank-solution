package com.bankingsolution.account.query.infrastructure.consumers.transaction;

import com.bankingsolution.common.events.FundsDepositedEvent;
import com.bankingsolution.common.events.FundsWithDrawnEvent;
import com.bankingsolution.common.events.TransactionCreatedEvent;
import com.bankingsolution.common.events.TransactionFailedEvent;
import org.springframework.messaging.handler.annotation.Payload;

public interface ITransactionEventConsumer {
    void consume(@Payload FundsDepositedEvent event);

    void consume(@Payload FundsWithDrawnEvent event);

    void consume(@Payload TransactionCreatedEvent event);

    void consume(@Payload TransactionFailedEvent event);
}
