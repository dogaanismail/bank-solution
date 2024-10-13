package com.banksolution.cqrs.core.handlers;

import com.banksolution.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);

    T getById(String id);

    void republishEvents();
}
