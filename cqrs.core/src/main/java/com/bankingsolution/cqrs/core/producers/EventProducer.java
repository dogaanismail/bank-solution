package com.bankingsolution.cqrs.core.producers;

import com.bankingsolution.cqrs.core.events.BaseEvent;

public interface EventProducer {

    void produce(String topic,
                 BaseEvent event
    );
}
