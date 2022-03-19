package com.bankingsolution.account.query.infrastructure.consumers;

import com.bankingsolution.account.query.infrastructure.handlers.EventHandler;
import com.bankingsolution.common.events.AccountOpenedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer {

    @Autowired
    private EventHandler eventHandler;

    public AccountEventConsumer(EventHandler eventHandler){
        this.eventHandler = eventHandler;
    }

    @RabbitListener(queues = "accounting:queue")
    @Override
    public void consume(AccountOpenedEvent event) {
        eventHandler.on(event);
    }
}
