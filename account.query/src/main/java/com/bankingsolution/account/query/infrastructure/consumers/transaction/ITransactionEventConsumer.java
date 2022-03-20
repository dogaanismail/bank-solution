package com.bankingsolution.account.query.infrastructure.consumers.transaction;

import com.bankingsolution.common.events.FundsDepositedEvent;
import com.bankingsolution.common.events.FundsWithDrawnEvent;
import org.springframework.messaging.handler.annotation.Payload;

public interface ITransactionEventConsumer {
    void consume(@Payload FundsDepositedEvent event);

    void consume(@Payload FundsWithDrawnEvent event);
}
