package com.banksolution.account.query.infrastructure.consumers.accounting;

import com.banksolution.account.query.infrastructure.handlers.accounting.IAccountEventHandler;
import com.banksolution.common.constants.Constants;
import com.banksolution.common.events.AccountOpenedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventConsumer implements IAccountEventConsumer {

    private final IAccountEventHandler accountEventHandler;

    @RabbitListener(queues = Constants.ACCOUNT_OPENED_EVENT_TOPIC)
    @Override
    public void consume(AccountOpenedEvent accountOpenedEvent) {
        accountEventHandler.on(accountOpenedEvent);
    }
}
