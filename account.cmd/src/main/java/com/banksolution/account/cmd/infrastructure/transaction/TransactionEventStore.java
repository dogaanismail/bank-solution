package com.banksolution.account.cmd.infrastructure.transaction;

import com.banksolution.account.cmd.factory.EventModelFactory;
import com.banksolution.account.cmd.repository.EventStoreRepository;
import com.banksolution.common.exceptions.AggregateNotFoundException;
import com.banksolution.common.exceptions.ConcurrencyException;
import com.banksolution.cqrs.core.events.BaseEvent;
import com.banksolution.account.cmd.domain.EventModel;
import com.banksolution.cqrs.core.infrastructure.EventStore;
import com.banksolution.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionEventStore implements EventStore {

    private final EventProducer eventProducer;
    private final EventStoreRepository repository;

    public TransactionEventStore(
            EventStoreRepository repository,
            @Qualifier("transactionEventProducer") EventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(
            String aggregateId,
            Iterable<BaseEvent> events,
            int expectedVersion) {

        List<EventModel> eventStream = repository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1
                && eventStream.getLast().getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }

        int version = expectedVersion;
        for (BaseEvent baseEvent : events) {

            version++;
            baseEvent.setVersion(version);

            EventModel eventModel = EventModelFactory.getEventModel(
                    baseEvent,
                    aggregateId,
                    version
            );

            EventModel persistedEvent = repository.save(eventModel);

            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(
                        baseEvent.getClass().getSimpleName(),
                        baseEvent
                );
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {

        List<EventModel> eventStream = repository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account ID provided!");
        }

        return eventStream.
                stream()
                .map(EventModel::getEventData)
                .toList();
    }

    @Override
    public List<String> getAggregateIds() {

        List<EventModel> eventStream = repository.findAll();

        if (eventStream.isEmpty()) {
            throw new IllegalStateException();
        }

        return eventStream.stream()
                .map(EventModel::getAggregateIdentifier)
                .distinct()
                .toList();
    }
}
