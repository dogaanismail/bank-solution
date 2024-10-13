package com.banksolution.cqrs.core.infrastructure;

import com.banksolution.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {

    void save(String aggregateId,
              Iterable<BaseEvent> events,
              int expectedVersion
    );

    List<BaseEvent> getEvents(String aggregateId);

    List<String> getAggregateIds();
}
