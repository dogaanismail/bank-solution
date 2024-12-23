package com.banksolution.account.cmd.infrastructure.accounting;

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

@Service
public class AccountEventStore implements EventStore {

    @Qualifier("accountEventProducer")
    private final EventProducer eventProducer;

    private final EventStoreRepository repository;

    public AccountEventStore(
            EventStoreRepository repository,
            @Qualifier("accountEventProducer") EventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(
            String aggregateId,
            Iterable<BaseEvent> events,
            int expectedVersion) {

        final var eventStream = repository.findByAggregateIdentifier(aggregateId);

        if (expectedVersion != -1
                && eventStream.getLast().getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }

        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            final var eventModel =
                    EventModel.builder()
                            .timestamp(LocalDateTime.now())
                            .aggregateIdentifier(aggregateId)
                            .aggregateType(AccountAggregate.class.getTypeName())
                            .version(version)
                            .eventType(event.getClass().getTypeName())
                            .eventData(event)
                            .build();

            final var persistedEvent = repository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        final var eventStream = repository.findByAggregateIdentifier(aggregateId);

        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account ID provided!");
        }

        return eventStream
                .stream()
                .map(EventModel::getEventData)
                .toList();
    }

    @Override
    public List<String> getAggregateIds() {
        final var eventStream = repository.findAll();

        if (eventStream.isEmpty()) {
            throw new IllegalStateException();
        }

        return eventStream.stream()
                .map(EventModel::getAggregateIdentifier)
                .distinct()
                .toList();
    }
}
