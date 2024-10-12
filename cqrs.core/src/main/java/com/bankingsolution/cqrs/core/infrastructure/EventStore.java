package com.bankingsolution.cqrs.core.infrastructure;

import com.bankingsolution.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {

    void save(String aggregateId,
              Iterable<BaseEvent> events,
              int expectedVersion
    );

    List<BaseEvent> getEvents(String aggregateId);

    List<String> getAggregateIds();
}
