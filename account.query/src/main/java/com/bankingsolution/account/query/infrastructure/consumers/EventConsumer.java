package com.bankingsolution.account.query.infrastructure.consumers;

import com.bankingsolution.common.events.AccountOpenedEvent;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event);
}
