package com.banksolution.account.cmd.infrastructure.transaction;

import com.banksolution.account.cmd.domain.AccountAggregate;
import com.banksolution.account.cmd.repository.EventStoreRepository;
import com.banksolution.common.exceptions.AggregateNotFoundException;
import com.banksolution.common.exceptions.ConcurrencyException;
import com.banksolution.cqrs.core.events.BaseEvent;
import com.banksolution.account.cmd.domain.EventModel;
import com.banksolution.cqrs.core.infrastructure.EventStore;
import com.banksolution.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public void save(String aggregateId,
                     Iterable<BaseEvent> events,
                     int expectedVersion) {

        var eventStream = repository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1
                && eventStream.getLast().getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel =
                    EventModel.builder()
                            .timestamp(LocalDateTime.now())
                            .aggregateIdentifier(aggregateId)
                            .aggregateType(AccountAggregate.class.getTypeName())
                            .version(version)
                            .eventType(event.getClass().getTypeName())
                            .eventData(event)
                            .build();

            var persistedEvent = repository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {

        var eventStream = repository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account ID provided!");
        }

        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {

        var eventStream = repository.findAll();
        if (eventStream.isEmpty()) {
            throw new IllegalStateException();
        }

        return eventStream.stream()
                .map(EventModel::getAggregateIdentifier)
                .distinct()
                .collect(Collectors.toList());
    }
}
