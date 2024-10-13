package com.banksolution.account.query.infrastructure.consumers.transaction;

import com.banksolution.account.query.infrastructure.handlers.transaction.ITransactionEventHandler;
import com.banksolution.common.events.FundsDepositedEvent;
import com.banksolution.common.events.FundsWithDrawnEvent;
import com.banksolution.common.events.TransactionCreatedEvent;
import com.banksolution.common.events.TransactionFailedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventConsumer implements ITransactionEventConsumer {
    private final ITransactionEventHandler transactionEventHandler;

    public TransactionEventConsumer(ITransactionEventHandler transactionEventHandler) {
        this.transactionEventHandler = transactionEventHandler;
    }

    @RabbitListener(queues = "FundsDepositedEvent")
    @Override
    public void consume(FundsDepositedEvent event) {
        transactionEventHandler.on(event);
    }

    @RabbitListener(queues = "FundsWithDrawnEvent")
    @Override
    public void consume(FundsWithDrawnEvent event) {
        transactionEventHandler.on(event);
    }

    @RabbitListener(queues = "TransactionCreatedEvent")
    @Override
    public void consume(TransactionCreatedEvent event) {
        transactionEventHandler.on(event);
    }

    @RabbitListener(queues = "TransactionFailedEvent")
    @Override
    public void consume(TransactionFailedEvent event) {
        transactionEventHandler.on(event);
    }
}
