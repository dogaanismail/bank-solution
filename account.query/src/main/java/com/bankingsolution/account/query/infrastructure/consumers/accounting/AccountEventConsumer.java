package com.bankingsolution.account.query.infrastructure.consumers.accounting;

import com.bankingsolution.account.query.infrastructure.handlers.accounting.IAccountEventHandler;
import com.bankingsolution.common.events.AccountOpenedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements IAccountEventConsumer {
    private final IAccountEventHandler accountEventHandler;

    public AccountEventConsumer(IAccountEventHandler accountEventHandler) {
        this.accountEventHandler = accountEventHandler;
    }

    @RabbitListener(queues = "AccountOpenedEvent")
    @Override
    public void consume(AccountOpenedEvent event) {
        accountEventHandler.on(event);
    }
}
