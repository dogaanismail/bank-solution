package com.banksolution.account.query.infrastructure.consumers.transaction;

import com.banksolution.common.events.FundsDepositedEvent;
import com.banksolution.common.events.FundsWithDrawnEvent;
import com.banksolution.common.events.TransactionCreatedEvent;
import com.banksolution.common.events.TransactionFailedEvent;
import org.springframework.messaging.handler.annotation.Payload;

public interface ITransactionEventConsumer {

    void consume(@Payload FundsDepositedEvent fundsDepositedEvent);

    void consume(@Payload FundsWithDrawnEvent fundsWithDrawnEvent);

    void consume(@Payload TransactionCreatedEvent transactionCreatedEvent);

    void consume(@Payload TransactionFailedEvent transactionFailedEvent);
}
