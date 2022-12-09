package com.bankingsolution.account.cmd.infrastructure.accounting;

import com.bankingsolution.cqrs.core.events.BaseEvent;
import com.bankingsolution.cqrs.core.producers.EventProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

    private final RabbitTemplate rabbitTemplate;

    public AccountEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String topic, BaseEvent event) {
        rabbitTemplate.convertAndSend(topic, event);
    }
}
