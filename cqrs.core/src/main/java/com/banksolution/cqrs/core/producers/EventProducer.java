package com.banksolution.cqrs.core.producers;

import com.banksolution.cqrs.core.events.BaseEvent;

public interface EventProducer {

    void produce(String topic,
                 BaseEvent event
    );
}
