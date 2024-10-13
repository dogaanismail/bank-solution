package com.banksolution.account.query.infrastructure.consumers.transaction;

import com.banksolution.account.query.infrastructure.handlers.transaction.ITransactionEventHandler;
import com.banksolution.common.constants.Constants;
import com.banksolution.common.events.FundsDepositedEvent;
import com.banksolution.common.events.FundsWithDrawnEvent;
import com.banksolution.common.events.TransactionCreatedEvent;
import com.banksolution.common.events.TransactionFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionEventConsumer implements ITransactionEventConsumer {

    private final ITransactionEventHandler transactionEventHandler;

    @RabbitListener(queues = Constants.FUNDS_DEPOSITED_EVENT_TOPIC)
    @Override
    public void consume(FundsDepositedEvent fundsDepositedEvent) {
        transactionEventHandler.on(fundsDepositedEvent);
    }

    @RabbitListener(queues = Constants.FUNDS_WITH_DRAWN_EVENT_TOPIC)
    @Override
    public void consume(FundsWithDrawnEvent fundsWithDrawnEvent) {
        transactionEventHandler.on(fundsWithDrawnEvent);
    }

    @RabbitListener(queues = Constants.TRANSACTION_CREATED_EVENT_TOPIC)
    @Override
    public void consume(TransactionCreatedEvent transactionCreatedEvent) {
        transactionEventHandler.on(transactionCreatedEvent);
    }

    @RabbitListener(queues = Constants.TRANSACTION_FAILED_EVENT_TOPIC)
    @Override
    public void consume(TransactionFailedEvent transactionFailedEvent) {
        transactionEventHandler.on(transactionFailedEvent);
    }
}
