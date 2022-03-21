package com.bankingsolution.account.query.infrastructure.consumers.transaction;

import com.bankingsolution.account.query.infrastructure.handlers.transaction.ITransactionEventHandler;
import com.bankingsolution.common.events.FundsDepositedEvent;
import com.bankingsolution.common.events.FundsWithDrawnEvent;
import com.bankingsolution.common.events.TransactionCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventConsumer implements ITransactionEventConsumer {

    @Autowired
    private ITransactionEventHandler transactionEventHandler;

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
}
