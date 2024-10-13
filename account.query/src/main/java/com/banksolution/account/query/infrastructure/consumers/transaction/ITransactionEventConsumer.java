package com.banksolution.account.query.infrastructure.consumers.transaction;

import com.banksolution.common.events.FundsDepositedEvent;
import com.banksolution.common.events.FundsWithDrawnEvent;
import com.banksolution.common.events.TransactionCreatedEvent;
import com.banksolution.common.events.TransactionFailedEvent;
import org.springframework.messaging.handler.annotation.Payload;

public interface ITransactionEventConsumer {
    void consume(@Payload FundsDepositedEvent event);

    void consume(@Payload FundsWithDrawnEvent event);

    void consume(@Payload TransactionCreatedEvent event);

    void consume(@Payload TransactionFailedEvent event);
}
