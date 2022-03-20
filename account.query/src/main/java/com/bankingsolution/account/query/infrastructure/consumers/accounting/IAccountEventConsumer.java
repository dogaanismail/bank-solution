package com.bankingsolution.account.query.infrastructure.consumers.accounting;

import com.bankingsolution.common.events.AccountOpenedEvent;
import org.springframework.messaging.handler.annotation.Payload;

public interface IAccountEventConsumer {
    void consume(@Payload AccountOpenedEvent event);
}
