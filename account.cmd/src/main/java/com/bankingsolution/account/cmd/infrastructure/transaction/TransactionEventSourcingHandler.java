package com.bankingsolution.account.cmd.infrastructure.transaction;

import com.bankingsolution.account.cmd.domain.AccountTransaction;
import com.bankingsolution.common.exceptions.TransactionNotFoundException;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import com.bankingsolution.cqrs.core.events.BaseEvent;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import com.bankingsolution.cqrs.core.infrastructure.EventStore;
import com.bankingsolution.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class TransactionEventSourcingHandler implements EventSourcingHandler<AccountTransaction> {

    private final EventStore eventStore;
    private final EventProducer eventProducer;

    public TransactionEventSourcingHandler(@Qualifier("transactionEventStore") EventStore eventStore,
                                           @Qualifier("transactionEventProducer") EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.save(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountTransaction getById(String id) {
        var aggregate = new AccountTransaction();
        var events = eventStore.getEvents(id);

        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);

            final var latestVersion = events.
                    stream()
                    .map(BaseEvent::getVersion)
                    .max(Comparator.naturalOrder())
                    .orElseThrow(() -> new TransactionNotFoundException("Transaction could not be found!"));

            aggregate.setVersion(latestVersion);
        }

        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.getActive()) continue;
            var events = eventStore.getEvents(aggregateId);
            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
