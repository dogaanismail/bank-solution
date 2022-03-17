package com.bankingsolution.cqrs.core.handlers;

import com.bankingsolution.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);

    T getById(String id);

    void republishEvents();
}
