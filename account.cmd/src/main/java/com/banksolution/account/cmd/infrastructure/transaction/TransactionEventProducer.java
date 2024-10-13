package com.banksolution.account.cmd.infrastructure.transaction;

import com.banksolution.cqrs.core.events.BaseEvent;
import com.banksolution.cqrs.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionEventProducer implements EventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produce(
            String topic,
            BaseEvent event) {
        
        rabbitTemplate.convertAndSend(topic, event);
    }
}
