package com.bankingsolution.account.cmd.infrastructure.transaction;

import com.bankingsolution.cqrs.core.events.BaseEvent;
import com.bankingsolution.cqrs.core.producers.EventProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventProducer implements EventProducer {
    private final RabbitTemplate rabbitTemplate;

    public TransactionEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String topic, BaseEvent event) {
        rabbitTemplate.convertAndSend(topic, event);
    }
}
