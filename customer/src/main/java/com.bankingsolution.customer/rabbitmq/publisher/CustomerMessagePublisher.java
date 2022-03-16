package com.bankingsolution.customer.rabbitmq.publisher;

import com.bankingsolution.common.constants.CommonContants;
import com.bankingsolution.customer.rabbitmq.data.CustomerMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CustomerMessagePublisher {
    private final RabbitTemplate rabbitTemplate;

    public CustomerMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    public void publishAccountCreatedEvent(String message) {
        rabbitTemplate.convertAndSend(CommonContants.TOPIC_KEY, CommonContants.CUSTOMER_KEY, message);
    }
}
